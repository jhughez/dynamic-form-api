package ie.joe.dynamic.dto;
import java.util.Collection;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActionDTO {

    private long actionValId;
    private long actionTypeId;
    private String actionName;
    private String performActionWhenValue;
    private Collection<Long> questionsToBeActioned;
}
