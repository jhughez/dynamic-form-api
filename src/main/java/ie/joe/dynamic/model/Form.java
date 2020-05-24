package ie.joe.dynamic.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "AGINSPECT_DATA", name = "TDAI_FORM")
@Getter
@Setter
public class Form implements Serializable {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Column(name = "FORM_ID") private long formId;
  @Column(name = "VISIT_ID") private long inspId;

  @ManyToOne
  @JoinColumn(name="TEMPLATE_ID")
  private FormTemplate formTemplate;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "answerPK.form")
  @JsonManagedReference
  private Collection<Answer> answers;

  public void setAnswers(Collection<Answer> answers) {
    answers.forEach(x -> x.getAnswerPK().setForm(this));
    this.answers = answers;
  }
}
