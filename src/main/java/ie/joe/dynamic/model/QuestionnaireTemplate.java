package ie.joe.dynamic.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "QUESTIONNAIRE_DATA", name = "QUESTIONNAIRE_TEMPLATE")
@Getter
@Setter
@EqualsAndHashCode
public class QuestionnaireTemplate implements Serializable {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Column(name = "TEMPLATE_ID") private long templateId;
  @Column(name = "TEMPLATE_TITLE") private String templateTitle;
  @Column(name = "TEMPLATE_DESC") private String templateDesc;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "questionnaireTemplate")
  @OrderBy("order")
  @JsonManagedReference
  private Collection<Section> section;


  public void setSection(Collection<Section> section) {
    section.forEach(x-> x.setQuestionnaireTemplate(this));
    this.section = section;
  }
}
