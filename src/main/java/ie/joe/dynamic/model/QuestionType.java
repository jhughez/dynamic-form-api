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
@Table(schema = "QUESTIONNAIRE_DATA", name = "QUESTION_TYPE")
@Getter
@Setter
@EqualsAndHashCode
public class QuestionType implements Serializable {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Column(name = "QUESTION_TYPE_ID") private long questionTypeId;
  @Column(name = "QUESTION_TYPE") private String questionTypeName;
  @Column(name = "QUESTION_TYPE_DESC") private String questionTypeDesc;
}
