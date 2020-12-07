package ie.joe.dynamic.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
public class ActionDTO {

    private long actionValId;
    private long actionTypeId;
    private String actionName;
    private String performActionWhenValue;
    private Collection<Long> questionsToBeActioned;
}
