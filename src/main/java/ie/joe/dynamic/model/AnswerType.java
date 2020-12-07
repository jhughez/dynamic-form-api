package ie.joe.dynamic.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(schema = "QUESTIONNAIRE_DATA", name = "ANSWER_TYPE")
@Getter
@Setter
@EqualsAndHashCode
public class AnswerType implements Serializable {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Column(name = "ANS_TYPE_ID") private long answerTypeId;
  @Column(name = "ANS_TYPE") private String answerTypeName;
  @Column(name = "ANS_TYPE_DESC") private String answerTypeDesc;
}
