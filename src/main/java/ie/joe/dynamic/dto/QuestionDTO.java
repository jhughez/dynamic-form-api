package ie.joe.dynamic.dto;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionDTO {

    private long questionId;
    private String questionLabel;
    private String questionDesc;
    private String questionIcon;
    private String questionPlaceholder;
    private long order;
    private String icon;
    private boolean readonly;
    private boolean mandatory;
    private boolean sensitive;
    private boolean visible;
    private long questionTypeId;
    private String questionTypeName;
    private long answerTypeId;
    private String answerTypeName;
    private Collection<AllowableValueDTO> allowableValues;
    private Collection<ValidationRuleDTO> validationRules;
    private Collection<ActionDTO> actionToPerquestionnaire;
    private String answer;
}
