package ie.joe.dynamic.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ie.joe.dynamic.dao.QuestionnaireTemplateRepository;
import ie.joe.dynamic.model.QuestionnaireTemplate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class QuestionnaireTemplateServiceTest {

  @Mock
  private QuestionnaireTemplateRepository questionnaireTemplateRepository;

  @InjectMocks
  private QuestionnaireTemplateService questionnaireTemplateService;

  @Test
  void findAllTest() {
    when(questionnaireTemplateRepository.findAll()).thenReturn(Collections.singletonList(new QuestionnaireTemplate()));
    List<QuestionnaireTemplate> questionnaireTemplates = questionnaireTemplateService.findAll();
    assertEquals(1, questionnaireTemplates.size(), "collection of questionnaireTemplates mustn't be empty");
  }

  @Test
  void findByIdTest() {
    when(questionnaireTemplateRepository.findById(anyLong())).thenReturn(Optional.of(new QuestionnaireTemplate()));
    QuestionnaireTemplate questionnaireTemplate = questionnaireTemplateService.findById(1).get();
    assertTrue(questionnaireTemplate instanceof QuestionnaireTemplate, "Expected instance of QuestionnaireTemplate to be returned");
  }

  @Test
  void saveTest() {
    QuestionnaireTemplate questionnaireTemplate = new QuestionnaireTemplate();
    questionnaireTemplate.setTemplateId(1);
    when(questionnaireTemplateRepository.save(questionnaireTemplate)).thenReturn(questionnaireTemplate);
    QuestionnaireTemplate returnQuestionnaireTemplate = questionnaireTemplateService.save(questionnaireTemplate);
    assertEquals(questionnaireTemplate, returnQuestionnaireTemplate, "Questionnaire does not equal return QuestionnaireTemplate");
  }

  @Test
  void deleteByIdTest() {
    long id = 1L;
    questionnaireTemplateService.deleteById(id);
    verify(questionnaireTemplateRepository).deleteById(id);
  }
}
