package ie.joe.dynamic.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import ie.joe.dynamic.dao.FormTemplateRepository;
import ie.joe.dynamic.model.FormTemplate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class FormTemplateServiceTest {

  @Mock
  private FormTemplateRepository formTemplateRepository;

  @InjectMocks
  private FormTemplateService formTemplateService;

  @Test
  void findAllTest() {
    when(formTemplateRepository.findAll()).thenReturn(Collections.singletonList(new FormTemplate()));
    List<FormTemplate> formTemplates = formTemplateService.findAll();
    assertEquals(1, formTemplates.size(), "collection of formTemplates mustn't be empty");
  }

  @Test
  void findByIdTest() {
    when(formTemplateRepository.findById(anyLong())).thenReturn(Optional.of(new FormTemplate()));
    FormTemplate formTemplate = formTemplateService.findById(1).get();
    assertTrue(formTemplate instanceof FormTemplate, "Expected instance of FormTemplate to be returned");
  }

  @Test
  void saveTest() {
    FormTemplate formTemplate = new FormTemplate();
    formTemplate.setTemplateId(1);
    when(formTemplateRepository.save(formTemplate)).thenReturn(formTemplate);
    FormTemplate returnFormTemplate = formTemplateService.save(formTemplate);
    assertEquals(formTemplate, returnFormTemplate, "Form does not equal return FormTemplate");
  }

  @Test
  void deleteByIdTest() {
    formTemplateService.deleteById(1L);
  }
}
