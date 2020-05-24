package ie.joe.dynamic.converter.dto;

import ie.joe.dynamic.model.Question;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class QuestionToIdConverter implements Converter<Question, Long> {

  @Override
  public Long convert(MappingContext<Question, Long> mappingContext) {
    return mappingContext.getSource() == null ? null : mappingContext.getSource().getQuestionId();
  }
}
