package ie.joe.dynamic.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import ie.joe.dynamic.converter.entity.BooleanToStringConverter;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "QUESTIONNAIRE_DATA", name = "QUESTION")
@Getter
@Setter
@EqualsAndHashCode
public class Question implements Serializable {

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "QUESTION_ID") private long questionId;
  @Column(name = "LABEL") private String questionLabel;
  @Column(name = "QUESTION_DESC") private String questionDesc;
  @Column(name = "QUESTION_ICON") private String questionIcon;
  @Column(name = "QUESTION_PLACEHOLDER") private String questionPlaceholder;
  @Column(name = "ORDER_NO") private long order;
  @Convert(converter= BooleanToStringConverter.class)
  @Column(name = "READONLY") private boolean readonly;
  @Convert(converter=BooleanToStringConverter.class)
  @Column(name = "MANDATORY") private boolean mandatory;
  @Convert(converter=BooleanToStringConverter.class)
  @Column(name = "SENSITIVE") private boolean sensitive;
  @Convert(converter=BooleanToStringConverter.class)
  @Column(name = "VISIBLE") private boolean visible;

  @ManyToOne
  @JoinColumn(name = "GROUP_ID", nullable = false, updatable = false)
  @JsonBackReference
  private QuestionnaireGroup group;

  @ManyToOne
  @JoinColumn(name="QUESTION_TYPE_ID")
  private QuestionType questionType;

  @ManyToOne
  @JoinColumn(name="ANS_TYPE_ID")
  private AnswerType answerType;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
  @JsonManagedReference
  private Collection<AllowableValue> allowableValues;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
  @JsonManagedReference
  private Collection<ValidationLink> validationRules;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
  @JsonManagedReference
  private Collection<ActionValue> actionToPerform;

  public void setValidationRules(Collection<ValidationLink> validationRules) {
    validationRules.forEach(x-> x.setQuestion(this));
    this.validationRules = validationRules;
  }

  public void setActionToPerform(Collection<ActionValue> actionToPerform) {
    actionToPerform.forEach(x-> x.setQuestion(this));
    this.actionToPerform = actionToPerform;
  }

  public void setAllowableValues(Collection<AllowableValue> allowableValues) {
      allowableValues.forEach(x -> x.setQuestion(this));
      this.allowableValues = allowableValues;
  }

  @Transient
  private String answer;
}
