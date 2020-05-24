package ie.joe.dynamic.service;

import ie.joe.dynamic.dao.QuestionnaireTemplateRepository;
import ie.joe.dynamic.model.QuestionnaireTemplate;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class QuestionnaireTemplateService {

  private final QuestionnaireTemplateRepository questionnaireTemplateRepository;

  public QuestionnaireTemplateService(QuestionnaireTemplateRepository questionnaireTemplateRepository) {
    this.questionnaireTemplateRepository = questionnaireTemplateRepository;
  }

  public List<QuestionnaireTemplate> findAll(){
    return questionnaireTemplateRepository.findAll();
  }

  public Optional<QuestionnaireTemplate> findById(long id){
    return questionnaireTemplateRepository.findById(id);
  }

  public QuestionnaireTemplate save( QuestionnaireTemplate questionnaireTemplate) {
    QuestionnaireTemplate returnQuestionnaireTemplate = questionnaireTemplateRepository.save(questionnaireTemplate);
    questionnaireTemplateRepository.refresh(returnQuestionnaireTemplate);
    return returnQuestionnaireTemplate;
  }

  public void deleteById(Long id) {
    questionnaireTemplateRepository.deleteById(id);
  }
}
