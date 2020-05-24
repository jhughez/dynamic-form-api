package ie.joe.dynamic.converter.dto;

import ie.joe.dynamic.dto.QuestionnaireDTO;
import ie.joe.dynamic.dto.QuestionnaireTemplateDTO;
import ie.joe.dynamic.model.Questionnaire;
import java.util.HashMap;
import java.util.Map;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;

public class QuestionnaireToQuestionnaireDTO implements Converter<Questionnaire, QuestionnaireDTO> {

  private final ModelMapper modelMapper;

  public QuestionnaireToQuestionnaireDTO(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  @Override
  public QuestionnaireDTO convert(MappingContext<Questionnaire, QuestionnaireDTO> mappingContext) {
    Questionnaire questionnaire = mappingContext.getSource();
    QuestionnaireDTO questionnaireDTO = new QuestionnaireDTO();

    Map<Long, String> answers = new HashMap<>();
    questionnaire.getAnswers().forEach(answer -> answers.put(answer.getAnswerPK().getQuestionId(), answer.getAnswerValue()));

    questionnaireDTO.setQuestionnaireId(questionnaire.getQuestionnaireId());
    questionnaireDTO.setQuestionnaireTemplate(modelMapper.map(questionnaire.getQuestionnaireTemplate(), QuestionnaireTemplateDTO.class));

    questionnaireDTO.getQuestionnaireTemplate().getSection()
        .forEach(sec ->
            sec.getGroup().forEach(grp ->
                grp.getQuestion().forEach(q ->
                    q.setAnswer(answers.get(q.getQuestionId())))));
    return questionnaireDTO;
  }
}
