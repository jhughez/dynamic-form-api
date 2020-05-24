package ie.joe.dynamic.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "AGINSPECT_DATA", name = "TSAI_ACTION_VALUE")
@Getter
@Setter
@EqualsAndHashCode
public class ActionValue implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ACT_VAL_ID") private long actionValId;

    @ManyToOne
    @JoinColumn(name="ACTION_ID")
    private ActionType actionType;

    @ManyToOne
    @JoinColumn(name="QUESTION_ID", nullable = false, updatable = false)
    @JsonBackReference
    private Question question;

    @ManyToMany
    @JoinTable(schema = "AGINSPECT_DATA", name = "TSAI_ACTION_LINK",
        joinColumns = {
            @JoinColumn(name = "ACT_VAL_ID", referencedColumnName = "ACT_VAL_ID",
                nullable = false, updatable = false)},
        inverseJoinColumns = {
            @JoinColumn(name = "QUESTION_ID", referencedColumnName = "QUESTION_ID",
                nullable = false, updatable = false)})
    private Collection<Question> questionsToBeActioned;

    @Column(name = "ACTION_VALUE")
    private String performActionWhenValue;
}
