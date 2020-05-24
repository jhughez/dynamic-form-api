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
@Table(schema = "AGINSPECT_DATA", name = "TSAI_VALIDATION_RULE")
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
