package ie.joe.dynamic.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ie.joe.dynamic.dao.QuestionnaireRepository;
import ie.joe.dynamic.model.Questionnaire;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class QuestionnaireServiceTest {

  @Mock
  private QuestionnaireRepository questionnaireRepository;

  @InjectMocks
  private QuestionnaireService questionnaireService;


  @Test
  void findByIdTest() {
    when(questionnaireRepository.findById(anyLong())).thenReturn(Optional.of(new Questionnaire()));
    Questionnaire questionnaire = questionnaireService.findById(1).get();
    assertTrue(questionnaire instanceof Questionnaire, "Expected instance of Questionnaire to be returned");
  }

  @Test
  void findAllTest() {
    when(questionnaireRepository.findAll()).thenReturn(Collections.singletonList(new Questionnaire()));
    List<Questionnaire> questionnaires = questionnaireService.findAll();
    assertEquals(1, questionnaires.size(), "collection of questionnaires mustn't be empty");
  }

  @Test
  void saveTest() {
    Questionnaire questionnaire = new Questionnaire();
    questionnaire.setQuestionnaireId(1);
    when(questionnaireRepository.save(questionnaire)).thenReturn(questionnaire);
    Questionnaire returnQuestionnaire = questionnaireService.save(questionnaire);
    assertEquals(questionnaire,returnQuestionnaire, "Questionnaire does not equal return questionnaire");
  }

  @Test
  void deleteByIdTest() {
    long id = 1L;
    questionnaireService.deleteById(id);
    verify(questionnaireRepository).deleteById(id);
  }

}
