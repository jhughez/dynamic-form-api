package ie.joe.dynamic.converter.dto;

import ie.joe.dynamic.dto.ValidationRuleDTO;
import ie.joe.dynamic.model.ValidationLink;
import ie.joe.dynamic.model.ValidationRule;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class ValidationRuleDTOToValidationLink implements Converter<ValidationRuleDTO, ValidationLink> {

  @Override
  public ValidationLink convert(MappingContext<ValidationRuleDTO, ValidationLink> mappingContext) {
    ValidationRuleDTO validationRuleDTO = mappingContext.getSource();

    ValidationRule validationRule = new ValidationRule();
    ValidationLink validationLink = new ValidationLink();

    validationRule.setRuleId(validationRuleDTO.getRuleId());
    validationRule.setRuleName(validationRuleDTO.getRuleName());
    validationRule.setRuleMessage(validationRuleDTO.getRuleMessage());

    validationLink.setValidationRule(validationRule);
    validationLink.setRuleValue(validationRuleDTO.getRuleValue());

    return validationLink;
  }
}
