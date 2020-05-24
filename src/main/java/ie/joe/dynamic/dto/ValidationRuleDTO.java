package ie.joe.dynamic.dto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationRuleDTO {

    private long ruleId;
    private String ruleName;
    private String ruleValue;
    private String ruleMessage;
}
