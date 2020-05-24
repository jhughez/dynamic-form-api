package ie.joe.dynamic.converter.dto;

import ie.joe.dynamic.dto.ValidationRuleDTO;
import ie.joe.dynamic.model.ValidationLink;
import ie.joe.dynamic.model.ValidationRule;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class ValidationLinkToValidationRuleDTO implements Converter<ValidationLink, ValidationRuleDTO> {

  @Override
  public ValidationRuleDTO convert(MappingContext<ValidationLink, ValidationRuleDTO> mappingContext) {
    ValidationLink validationLink = mappingContext.getSource();
    ValidationRule validationRule = validationLink.getValidationRule();
    ValidationRuleDTO validationRuleDTO = new ValidationRuleDTO();

    validationRuleDTO.setRuleId(validationRule.getRuleId());
    validationRuleDTO.setRuleName(validationRule.getRuleName());
    validationRuleDTO.setRuleMessage(validationRule.getRuleMessage());
    validationRuleDTO.setRuleValue(validationLink.getRuleValue());

    return validationRuleDTO;
  }
}
