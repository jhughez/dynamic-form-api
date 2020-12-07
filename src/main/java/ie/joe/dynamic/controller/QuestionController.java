package ie.joe.dynamic.controller;

import ie.joe.dynamic.constants.ApiVersion;
import ie.joe.dynamic.dto.QuestionDTO;
import ie.joe.dynamic.model.Question;
import ie.joe.dynamic.service.QuestionService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/" + ApiVersion.CURRENT + "/questions")
@Log4j2
public class QuestionController {

  private static final String ID_ALREADY_EXISTS = "Id [ %d ] is not existed";

  private QuestionService questionService;
  private ModelMapper modelMapper;

  public QuestionController(QuestionService questionService, ModelMapper modelMapper) {
    this.questionService = questionService;
    this.modelMapper = modelMapper;
  }

  @GetMapping
  public ResponseEntity<List<QuestionDTO>> findAll(){
    return ResponseEntity.ok(
        questionService.findAll().stream()
            .map(entity -> modelMapper.map(entity, QuestionDTO.class))
            .collect(Collectors.toList())
    );
  }

  @GetMapping("/{id}")
  public ResponseEntity<QuestionDTO> findById(@PathVariable long id){
    Optional<Question> question = questionService.findById(id);
    if (!question.isPresent()) {
      throw new NoSuchElementException("Unable to find Question with id: [" + id + "]");
    }
    return ResponseEntity.ok(
        modelMapper.map(question.get(), QuestionDTO.class)
    );
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    Question question = questionService.findById(id).orElse(null);
    if (!questionService.findById(id).isPresent()) {
      log.error(String.format(ID_ALREADY_EXISTS, id));
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    questionService.delete(question);
  }

  @DeleteMapping("/{id}/allowableValues/{value}")
  public void deleteAllowableValue(@PathVariable Long id, @PathVariable String value) {
    Optional<Question> question = questionService.findById(id);
    if (!question.isPresent()) {
      log.error(String.format(ID_ALREADY_EXISTS, id));
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    questionService.deleteAllowableValue(question.get(), value);
  }

  @DeleteMapping("/{questionId}/validationRules/{ruleId}")
  public void deleteValidationRules(@PathVariable Long questionId, @PathVariable Long ruleId) {
    Optional<Question> question = questionService.findById(questionId);
    if (!question.isPresent()) {
      log.error(String.format(ID_ALREADY_EXISTS, questionId));
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    questionService.deleteValidationRule(question.get(), ruleId);
  }

  @DeleteMapping("/{questionId}/actionToPerquestionnaire/{actionId}")
  public void deleteActionToPerquestionnaire(@PathVariable Long questionId, @PathVariable Long actionId) {
    questionService.deleteActionToPerquestionnaire(actionId);
  }
}
