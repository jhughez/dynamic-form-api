package ie.joe.dynamic.service;

import ie.joe.dynamic.model.AllowableValue.AllowableValuePK;
import ie.joe.dynamic.model.Question;
import ie.joe.dynamic.model.ValidationLink.ValidationLinkPK;
import ie.joe.dynamic.model.ValidationRule;
import ie.joe.dynamic.dao.ActionValueRepository;
import ie.joe.dynamic.dao.AllowableValueRepository;
import ie.joe.dynamic.dao.QuestionRepository;
import ie.joe.dynamic.dao.ValidationLinkRepository;
import ie.joe.dynamic.dao.ValidationRuleRepository;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class QuestionService {

  private QuestionRepository questionRepository;
  private AllowableValueRepository allowableValueRepository;
  private ValidationLinkRepository validationLinkRepository;
  private ValidationRuleRepository validationRuleRepository;
  private ActionValueRepository actionValueRepository;

  public QuestionService(QuestionRepository questionRepository,
      AllowableValueRepository allowableValueRepository,
      ValidationLinkRepository validationLinkRepository,
      ValidationRuleRepository validationRuleRepository,
      ActionValueRepository actionValueRepository
  ) {
    this.questionRepository = questionRepository;
    this.allowableValueRepository = allowableValueRepository;
    this.validationLinkRepository = validationLinkRepository;
    this.validationRuleRepository = validationRuleRepository;
    this.actionValueRepository = actionValueRepository;
  }

  public List<Question> findAll(){
    return questionRepository.findAll();
  }

  public Optional<Question> findById(long id){
    return questionRepository.findById(id);
  }

  public void deleteById(Long id) {
    questionRepository.deleteById(id);
  }

  public void delete(Question question) {
    question.getGroup().getQuestion().remove(question);
    questionRepository.delete(question);
  }

  public void deleteAllowableValue(Question question, String value) {
    allowableValueRepository.deleteById(new AllowableValuePK(question.getQuestionId(), value));
  }

  public void deleteValidationRule(Question question, Long ruleId) {
    ValidationRule validationRule = validationRuleRepository.findById(ruleId).orElseThrow(EntityNotFoundException::new);
    validationLinkRepository.deleteById(new ValidationLinkPK(question.getQuestionId(), validationRule.getRuleId()));
  }

  public void deleteActionToPerform(Long actionValId) {
    actionValueRepository.deleteById(actionValId);
  }

}
