package ie.joe.dynamic.converter.dto;

import ie.joe.dynamic.dto.QuestionnaireUserResponseDTO;
import ie.joe.dynamic.model.Answer;
import ie.joe.dynamic.model.Answer.AnswerPK;
import ie.joe.dynamic.model.Questionnaire;
import ie.joe.dynamic.model.QuestionnaireTemplate;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class QuestionnaireUserResponseDTOToQuestionnaire implements Converter<QuestionnaireUserResponseDTO, Questionnaire> {

  @Override
  public Questionnaire convert(MappingContext<QuestionnaireUserResponseDTO, Questionnaire> mappingContext) {
    QuestionnaireUserResponseDTO questionnaireUserResponseDTO = mappingContext.getSource();
    Questionnaire questionnaire = new Questionnaire();
    QuestionnaireTemplate questionnaireTemplate = new QuestionnaireTemplate();
    questionnaireTemplate.setTemplateId(questionnaireUserResponseDTO.getTemplateId());
    questionnaire.setQuestionnaireTemplate(questionnaireTemplate);
    questionnaire.setQuestionnaireId(questionnaireUserResponseDTO.getQuestionnaireId());
    List<Answer> answers = questionnaireUserResponseDTO.getAnswers()
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

    questionnaire.setAnswers(answers);
    return questionnaire;
  }
}
