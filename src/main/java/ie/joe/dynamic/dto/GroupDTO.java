package ie.joe.dynamic.dto;
import ie.joe.dynamic.model.GroupType;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupDTO {

    private Long groupId;
    private String groupTitle;
    private Long order;
    private GroupType groupType;
    private Collection<QuestionDTO> question;
}
