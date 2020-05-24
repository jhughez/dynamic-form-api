package ie.joe.dynamic.dto;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SectionDTO {

    private long sectionId;
    private String sectionTitle;
    private long order;
    private Collection<GroupDTO> group;
}
