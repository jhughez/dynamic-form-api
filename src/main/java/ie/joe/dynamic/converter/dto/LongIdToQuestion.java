package ie.joe.dynamic.converter.dto;

import ie.joe.dynamic.model.Question;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class LongIdToQuestion implements Converter<Long, Question> {

  @Override
  public Question convert(MappingContext<Long, Question> mappingContext) {
    Question question = new Question();
    question.setQuestionId(mappingContext.getSource());
    return question;
  }
}
