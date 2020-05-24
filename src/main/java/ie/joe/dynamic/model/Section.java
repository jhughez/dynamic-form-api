package ie.joe.dynamic.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(schema = "AGINSPECT_DATA", name = "TSAI_SECTION")
@Getter
@Setter
@EqualsAndHashCode
public class Section  implements Serializable {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Column(name = "SECTION_ID") private long sectionId;
  @Column(name = "SECTION_TITLE") private String sectionTitle;
  @Column(name = "ORDER_NO") private long order;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "section")
  @Fetch(FetchMode.SELECT)
  @OrderBy("order")
  @JsonManagedReference
  private Collection<Group> group;

  @ManyToOne
  @JoinColumn(name = "FORM_ID", nullable = false, updatable = false)
  @JsonBackReference
  private FormTemplate formTemplate;

  public void setGroup(Collection<Group> group) {
    group.forEach(x-> x.setSection(this));
    this.group = group;
  }
}
