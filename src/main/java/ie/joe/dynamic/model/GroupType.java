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
