package ie.joe.dynamic.converter.dto;

import ie.joe.dynamic.dto.AllowableValueDTO;
import ie.joe.dynamic.model.AllowableValue;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class AllowableValueDTOToAllowableValue implements Converter<AllowableValueDTO, AllowableValue> {

  @Override
  public AllowableValue convert(MappingContext<AllowableValueDTO, AllowableValue> mappingContext) {
    AllowableValueDTO allowableValueDTO = mappingContext.getSource();
    AllowableValue allowableValue = new AllowableValue();
    allowableValue.setOptionName(allowableValueDTO.getOptionName());
    allowableValue.setValue(allowableValueDTO.getValue());
    return allowableValue;
  }
}
