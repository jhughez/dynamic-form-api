package ie.joe.dynamic.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(schema = "AGINSPECT_DATA", name = "TSAI_VALIDATION_LINK")
@Getter
@Setter
@EqualsAndHashCode
@IdClass(ValidationLink.ValidationLinkPK.class)
public class ValidationLink implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name="QUESTION_ID", nullable = false, updatable = false)
    @JsonBackReference
    private Question question;

    @Id
    @ManyToOne
    @JoinColumn(name="RULE_ID")
    private ValidationRule validationRule;

    @Column(name = "RULE_VALUE")
    private String ruleValue;


    @Getter
    @Setter
    @EqualsAndHashCode
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ValidationLinkPK implements Serializable {

        private long question;
        private long validationRule;
    }

}
