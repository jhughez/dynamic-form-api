package ie.joe.dynamic.model;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "QUESTIONNAIRE_DATA", name = "GROUP_TYPE")
@Getter
@Setter
@EqualsAndHashCode
public class GroupType implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "GROUP_TYPE_ID") private long groupTypeId;
    @Column(name = "GROUP_TYPE") private String groupTypeName;
    @Column(name = "GROUP_TYPE_DESC") private String groupTypeDesc;
}
