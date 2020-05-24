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
@Table(schema = "QUESTIONNAIRE_DATA", name = "QUESTIONNAIRE")
@Getter
@Setter
public class Questionnaire implements Serializable {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Column(name = "QUESTIONNAIRE_ID") private long questionnaireId;

  @ManyToOne
  @JoinColumn(name="TEMPLATE_ID")
  private QuestionnaireTemplate questionnaireTemplate;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "answerPK.questionnaire")
  @JsonManagedReference
  private Collection<Answer> answers;

  public void setAnswers(Collection<Answer> answers) {
    answers.forEach(x -> x.getAnswerPK().setQuestionnaire(this));
    this.answers = answers;
  }
}
