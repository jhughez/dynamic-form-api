package ie.joe.dynamic.converter.dto;

import ie.joe.dynamic.dto.FormDTO;
import ie.joe.dynamic.dto.FormTemplateDTO;
import ie.joe.dynamic.model.Form;
import java.util.HashMap;
import java.util.Map;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;

public class FormToFormDTO implements Converter<Form, FormDTO> {

  private final ModelMapper modelMapper;

  public FormToFormDTO(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  @Override
  public FormDTO convert(MappingContext<Form, FormDTO> mappingContext) {
    Form form = mappingContext.getSource();
    FormDTO formDTO = new FormDTO();

    Map<Long, String> answers = new HashMap<>();
    form.getAnswers().forEach(answer -> answers.put(answer.getAnswerPK().getQuestionId(), answer.getAnswerValue()));

    formDTO.setFormId(form.getFormId());
    formDTO.setInspId(form.getInspId());
    formDTO.setFormTemplate(modelMapper.map(form.getFormTemplate(), FormTemplateDTO.class));

    formDTO.getFormTemplate().getSection()
        .forEach(sec ->
            sec.getGroup().forEach(grp ->
                grp.getQuestion().forEach(q ->
                    q.setAnswer(answers.get(q.getQuestionId())))));
    return formDTO;
  }
}
