package ie.joe.dynamic.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormDTO {
  private long formId;
  private long inspId;
  private FormTemplateDTO formTemplate;
}
