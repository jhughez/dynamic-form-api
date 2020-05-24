package ie.joe.dynamic.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "QUESTIONNAIRE_DATA", name = "ANSWER")
@Getter
@Setter
@EqualsAndHashCode
public class Answer implements Serializable {


  @EmbeddedId
  private AnswerPK answerPK;
  @Column(name = "ANSWER") private String answerValue;

  @Embeddable
  @Getter
  @Setter
  @EqualsAndHashCode
  public static class AnswerPK implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="QUESTIONNAIRE_ID")
    @JsonBackReference
    private Questionnaire questionnaire;

    @Column(name="QUESTION_ID")
    private long questionId;
  }
}
