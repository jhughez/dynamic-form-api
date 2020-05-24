package ie.joe.dynamic.config;

import ie.joe.dynamic.converter.dto.AllowableValueDTOToAllowableValue;
import ie.joe.dynamic.converter.dto.QuestionnaireToQuestionnaireDTO;
import ie.joe.dynamic.converter.dto.QuestionnaireUserResponseDTOToQuestionnaire;
import ie.joe.dynamic.converter.dto.LongIdToQuestion;
import ie.joe.dynamic.converter.dto.QuestionToIdConverter;
import ie.joe.dynamic.converter.dto.ValidationLinkToValidationRuleDTO;
import ie.joe.dynamic.converter.dto.ValidationRuleDTOToValidationLink;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

  @Bean
  public ModelMapper getModelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.addConverter(new QuestionToIdConverter());
    modelMapper.addConverter(new LongIdToQuestion());
    modelMapper.addConverter(new AllowableValueDTOToAllowableValue());
    modelMapper.addConverter(new ValidationRuleDTOToValidationLink());
    modelMapper.addConverter(new ValidationLinkToValidationRuleDTO());
    modelMapper.addConverter(new QuestionnaireToQuestionnaireDTO(modelMapper));
    modelMapper.addConverter(new QuestionnaireUserResponseDTOToQuestionnaire());
    return modelMapper;
  }
}
