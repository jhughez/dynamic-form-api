package ie.joe.dynamic.converter.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class BooleanToStringConverterTest{

  @InjectMocks private BooleanToStringConverter booleanToStringConverter;

  @Test
  void convertToDatabaseColumnTest() {
    assertEquals("Y", booleanToStringConverter.convertToDatabaseColumn(true),  "true must return Y");
    assertEquals("N", booleanToStringConverter.convertToDatabaseColumn(false), "false must return N");
  }

  @Test
  void convertToEntityAttributeTest() {
    assertTrue(booleanToStringConverter.convertToEntityAttribute("Y"), "Y must return true");
    assertFalse(booleanToStringConverter.convertToEntityAttribute("N"), "N must return false");
  }
}
