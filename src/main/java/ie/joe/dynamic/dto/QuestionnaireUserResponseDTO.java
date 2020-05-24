package ie.joe.dynamic.dto;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionnaireUserResponseDTO {
  private long questionnaireId;
  private long templateId;
  private Collection<AnswerDTO> answers;
}
