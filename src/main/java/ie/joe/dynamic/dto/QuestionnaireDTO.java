package ie.joe.dynamic.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionnaireDTO {
  private long questionnaireId;
  private QuestionnaireTemplateDTO questionnaireTemplate;
}
