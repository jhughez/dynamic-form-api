package ie.joe.dynamic.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(schema = "AGINSPECT_DATA", name = "TSAI_ALLOWABLE_VALUE")
@Getter
@Setter
@EqualsAndHashCode
@IdClass(AllowableValue.AllowableValuePK.class)
public class AllowableValue implements Serializable {

  @Column(name = "VALUE_NAME") private String optionName;

  @Id
  @Column(name = "VALUE") private String value;

  @Id
  @ManyToOne
  @JoinColumn(name="QUESTION_ID", nullable = false, updatable = false)
  @JsonBackReference
  private Question question;

  @Getter
  @Setter
  @EqualsAndHashCode
  @AllArgsConstructor
  @NoArgsConstructor
  public static class AllowableValuePK implements Serializable {
    private long question;
    private String value;
  }
}
