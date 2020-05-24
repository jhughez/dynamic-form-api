package ie.joe.dynamic.config;

import ie.joe.dynamic.converter.dto.AllowableValueDTOToAllowableValue;
import ie.joe.dynamic.converter.dto.FormToFormDTO;
import ie.joe.dynamic.converter.dto.FormUserResponseDTOToForm;
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
    modelMapper.addConverter(new FormToFormDTO(modelMapper));
    modelMapper.addConverter(new FormUserResponseDTOToForm());
    return modelMapper;
  }
}
