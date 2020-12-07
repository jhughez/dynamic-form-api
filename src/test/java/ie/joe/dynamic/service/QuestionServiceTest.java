package ie.joe.dynamic.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ie.joe.dynamic.dao.ActionValueRepository;
import ie.joe.dynamic.dao.AllowableValueRepository;
import ie.joe.dynamic.dao.QuestionRepository;
import ie.joe.dynamic.dao.ValidationLinkRepository;
import ie.joe.dynamic.dao.ValidationRuleRepository;
import ie.joe.dynamic.model.AllowableValue;
import ie.joe.dynamic.model.Question;
import ie.joe.dynamic.model.ValidationLink;
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
    long id = 1L;
    questionService.deleteById(id);
    verify(questionRepository).deleteById(id);
  }

  @Test
  void deleteAllowableValueTest() {
    Question question = new Question();
    String value = "test";
    questionService.deleteAllowableValue(question, value);
    verify(allowableValueRepository).deleteById(any(AllowableValue.AllowableValuePK.class));
  }

  @Test
  void deleteValidationRuleTest() {
    long ruleId = 1;
    when(validationRuleRepository.findById(ruleId)).thenReturn(Optional.of(new ValidationRule()));
    questionService.deleteValidationRule(new Question(), ruleId);
    verify(validationLinkRepository).deleteById(any(ValidationLink.ValidationLinkPK.class));
  }

  @Test
  void deleteActionToPerquestionnaireTest() {
    long actionValId = 1L;
    questionService.deleteActionToPerquestionnaire(actionValId);
    verify(actionValueRepository).deleteById(actionValId);
  }

}
