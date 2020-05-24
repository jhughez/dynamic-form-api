package ie.joe.dynamic.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import ie.joe.dynamic.dao.FormRepository;
import ie.joe.dynamic.model.Form;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class FormServiceTest {

  @Mock
  private FormRepository formRepository;

  @InjectMocks
  private FormService formService;


  @Test
  void findByInspIdTest() {
    when(formRepository.findByInspId(anyLong())).thenReturn(Optional.of(new Form()));
    Form form = formService.findByInspId(1).get();
    assertTrue(form instanceof Form, "Expected instance of Form to be returned");
  }

  @Test
  void findByIdTest() {
    when(formRepository.findById(anyLong())).thenReturn(Optional.of(new Form()));
    Form form = formService.findById(1).get();
    assertTrue(form instanceof Form, "Expected instance of Form to be returned");
  }

  @Test
  void findAllTest() {
    when(formRepository.findAll()).thenReturn(Collections.singletonList(new Form()));
    List<Form> forms = formService.findAll();
    assertEquals(1, forms.size(), "collection of forms mustn't be empty");
  }

  @Test
  void saveTest() {
    Form form = new Form();
    form.setFormId(1);
    when(formRepository.save(form)).thenReturn(form);
    Form returnForm = formService.save(form);
    assertEquals(form,returnForm, "Form does not equal return form");
  }

  @Test
  void deleteByIdTest() {
    formService.deleteById(1L);
  }

}
