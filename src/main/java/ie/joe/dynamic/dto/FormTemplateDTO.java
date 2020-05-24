package ie.joe.dynamic.dto;

import java.util.Collection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormTemplateDTO {
    private long templateId;
    private String templateTitle;
    private String templateDesc;
    private String resultFormId;
    private Collection<SectionDTO> section;
}
