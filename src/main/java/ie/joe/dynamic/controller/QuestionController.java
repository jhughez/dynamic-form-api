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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/" + ApiVersion.CURRENT + "/questions")
@Log4j2
public class QuestionController {

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
  public ResponseEntity delete(@PathVariable Long id) {
    Question question = questionService.findById(id).orElse(null);
    if (!questionService.findById(id).isPresent()) {
      log.error("Id " + id + " is not existed");
      return ResponseEntity.badRequest().build();
    }
    questionService.delete(question);

    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}/allowableValues/{value}")
  public ResponseEntity deleteAllowableValue(@PathVariable Long id, @PathVariable String value) {
    Optional<Question> question = questionService.findById(id);
    if (!question.isPresent()) {
      log.error("Id " + id + " is not existed");
      return ResponseEntity.badRequest().build();
    }
    questionService.deleteAllowableValue(question.get(), value);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{questionId}/validationRules/{ruleId}")
  public ResponseEntity deleteValidationRules(@PathVariable Long questionId, @PathVariable Long ruleId) {
    Optional<Question> question = questionService.findById(questionId);
    if (!question.isPresent()) {
      log.error("Id " + questionId + " is not existed");
      return ResponseEntity.badRequest().build();
    }

    questionService.deleteValidationRule(question.get(), ruleId);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{questionId}/actionToPerquestionnaire/{actionId}")
  public ResponseEntity deleteActionToPerquestionnaire(@PathVariable Long questionId, @PathVariable Long actionId) {
    questionService.deleteActionToPerquestionnaire(actionId);
    return ResponseEntity.ok().build();
  }
}
