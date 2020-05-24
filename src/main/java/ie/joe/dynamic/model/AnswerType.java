package ie.joe.dynamic.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "AGINSPECT_DATA", name = "TSAI_ANSWER_TYPE")
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
