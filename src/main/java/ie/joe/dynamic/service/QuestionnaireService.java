package ie.joe.dynamic.service;

import ie.joe.dynamic.model.Questionnaire;
import ie.joe.dynamic.dao.QuestionnaireRepository;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class QuestionnaireService {

  private final QuestionnaireRepository questionnaireRepository;

  public QuestionnaireService(QuestionnaireRepository questionnaireRepository) {
    this.questionnaireRepository = questionnaireRepository;
  }

  public Optional<Questionnaire> findById(long id) {
    return questionnaireRepository.findById(id);
  }

  public List<Questionnaire> findAll() {
    return questionnaireRepository.findAll();
  }

  public Questionnaire save( Questionnaire questionnaire) {
    Questionnaire returnQuestionnaire = questionnaireRepository.save(questionnaire);
    questionnaireRepository.refresh(returnQuestionnaire);
    return returnQuestionnaire;
  }

  public void deleteById(Long id) {
    questionnaireRepository.deleteById(id);
  }
}
