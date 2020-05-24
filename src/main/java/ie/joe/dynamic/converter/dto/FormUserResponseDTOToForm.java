package ie.joe.dynamic.converter.dto;

import ie.joe.dynamic.dto.FormUserResponseDTO;
import ie.joe.dynamic.model.Answer;
import ie.joe.dynamic.model.Answer.AnswerPK;
import ie.joe.dynamic.model.Form;
import ie.joe.dynamic.model.FormTemplate;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class FormUserResponseDTOToForm implements Converter<FormUserResponseDTO, Form> {

  @Override
  public Form convert(MappingContext<FormUserResponseDTO, Form> mappingContext) {
    FormUserResponseDTO formUserResponseDTO = mappingContext.getSource();
    Form form = new Form();
    FormTemplate formTemplate = new FormTemplate();
    formTemplate.setTemplateId(formUserResponseDTO.getTemplateId());
    form.setFormTemplate(formTemplate);
    form.setInspId(formUserResponseDTO.getInspId());
    form.setFormId(formUserResponseDTO.getFormId());
    List<Answer> answers = formUserResponseDTO.getAnswers()
        .stream()
        .filter(answerDTO -> answerDTO.getAnswerValue() != null)
        .map(answerDto -> {
      Answer answer = new Answer();
      AnswerPK answerPK = new AnswerPK();
      answerPK.setQuestionId(answerDto.getQuestionId());
      answer.setAnswerPK(answerPK);
      answer.setAnswerValue(answerDto.getAnswerValue());

      return answer;
    }).collect(Collectors.toList());

    form.setAnswers(answers);
    return form;
  }
}
