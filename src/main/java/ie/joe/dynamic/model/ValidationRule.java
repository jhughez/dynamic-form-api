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
@Table(schema = "QUESTIONNAIRE_DATA", name = "VALIDATION_RULE")
@Getter
@Setter
@EqualsAndHashCode
public class ValidationRule implements Serializable {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Column(name = "RULE_ID") private long ruleId;
  @Column(name = "RULE_NAME") private String ruleName;
  @Column(name = "RULE_MESSAGE") private String ruleMessage;
}
