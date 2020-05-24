package ie.joe.dynamic.dto;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormUserResponseDTO {
  private long formId;
  private long inspId;
  private long templateId;
  private Collection<AnswerDTO> answers;
}
