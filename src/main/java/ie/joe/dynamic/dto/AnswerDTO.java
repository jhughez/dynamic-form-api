package ie.joe.dynamic.dto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerDTO {
  private long questionId;
  private String answerValue;
}
