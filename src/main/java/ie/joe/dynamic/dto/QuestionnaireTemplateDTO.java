package ie.joe.dynamic.dto;

import java.util.Collection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionnaireTemplateDTO {
    private long templateId;
    private String templateTitle;
    private String templateDesc;
    private Collection<SectionDTO> section;
}
