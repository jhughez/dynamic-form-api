package ie.joe.dynamic.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "QUESTIONNAIRE_DATA", name = "QUESTIONNAIRE_GROUP")
@Getter
@Setter
@EqualsAndHashCode
public class QuestionnaireGroup implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "GROUP_ID") private Long groupId;
    @Column(name = "GROUP_TITLE") private String groupTitle;
    @Column(name = "ORDER_NO") private Long order;

    @ManyToOne
    @JoinColumn(name="GROUP_TYPE_ID")
    private GroupType groupType;

    @ManyToOne(optional=false)
    @JoinColumn(name = "SECTION_ID", nullable = false, updatable = false)
    @JsonBackReference
    private Section section;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "group")
    @OrderBy("order")
    @JsonManagedReference
    private Collection<Question> question;

    public void setQuestion(Collection<Question> question) {
        question.forEach(x-> x.setGroup(this));
        this.question = question;
    }
}
