package ie.joe.dynamic.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import ie.joe.dynamic.dao.ActionValueRepository;
import ie.joe.dynamic.dao.AllowableValueRepository;
import ie.joe.dynamic.dao.QuestionRepository;
import ie.joe.dynamic.dao.ValidationLinkRepository;
import ie.joe.dynamic.dao.ValidationRuleRepository;
import ie.joe.dynamic.model.Question;
import ie.joe.dynamic.model.ValidationRule;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class QuestionServiceTest {

  @Mock private QuestionRepository questionRepository;
  @Mock private AllowableValueRepository allowableValueRepository;
  @Mock private ValidationLinkRepository validationLinkRepository;
  @Mock private ValidationRuleRepository validationRuleRepository;
  @Mock private ActionValueRepository actionValueRepository;

  @InjectMocks
  private QuestionService questionService;

  @Test
  void findAllTest() {
    when(questionRepository.findAll()).thenReturn(Collections.singletonList(new Question()));
    List<Question> questions = questionService.findAll();
    assertEquals(1, questions.size(), "collection of questions mustn't be empty");
  }

  @Test
  void findByIdTest() {
    when(questionRepository.findById(anyLong())).thenReturn(Optional.of(new Question()));
    Question question = questionService.findById(1).get();
    assertTrue(question instanceof Question, "Expected instance of Question to be returned");
  }

  @Test
  void deleteByIdTest() {
    questionService.deleteById(1L);
  }

  @Test
  void deleteAllowableValueTest() {
    questionService.deleteAllowableValue(new Question(), "test");
  }

  @Test
  void deleteValidationRuleTest() {
    long ruleId = 1;
    when(validationRuleRepository.findById(ruleId)).thenReturn(Optional.of(new ValidationRule()));
    questionService.deleteValidationRule(new Question(), ruleId);
  }

  @Test
  void deleteActionToPerquestionnaireTest() {
    questionService.deleteActionToPerquestionnaire(1L);
  }

}
