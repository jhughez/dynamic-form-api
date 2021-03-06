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
@Table(schema = "QUESTIONNAIRE_DATA", name = "ACTION")
@Getter
@Setter
@EqualsAndHashCode
public class ActionType implements Serializable {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Column(name = "ACTION_ID") private long actionTypeId;
  @Column(name = "ACTION_NAME") private String actionName;
}
