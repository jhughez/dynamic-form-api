package ie.joe.dynamic.controller;

import ie.joe.dynamic.constants.ApiVersion;
import ie.joe.dynamic.dto.QuestionnaireDTO;
import ie.joe.dynamic.dto.QuestionnaireUserResponseDTO;
import ie.joe.dynamic.model.Questionnaire;
import ie.joe.dynamic.service.QuestionnaireService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/" + ApiVersion.CURRENT + "/questionnaires")
@Log4j2
public class QuestionnaireController {

  private final QuestionnaireService questionnaireService;
  private final ModelMapper modelMapper;

  public QuestionnaireController(QuestionnaireService questionnaireService, ModelMapper modelMapper){
    this.questionnaireService = questionnaireService;
    this.modelMapper = modelMapper;
  }

  @GetMapping
  public ResponseEntity<List<QuestionnaireDTO>> findAll(){
    return ResponseEntity.ok(
        questionnaireService.findAll().stream()
            .map(entity -> modelMapper.map(entity, QuestionnaireDTO.class))
            .collect(Collectors.toList())
    );
  }

  @GetMapping("/{id}")
  public ResponseEntity<QuestionnaireDTO> findById(@PathVariable long id){
    Optional<Questionnaire> questionnaire = questionnaireService.findById(id);
    if (!questionnaire.isPresent()) {
      throw new NoSuchElementException("Unable to find questionnaire with id: [" + id + "]");
    }
    return ResponseEntity.ok(
        modelMapper.map(questionnaire.get(), QuestionnaireDTO.class)
    );
  }

  @PostMapping
  public ResponseEntity create(@Valid @RequestBody QuestionnaireUserResponseDTO questionnaireUserResponseDTO) {
    if(questionnaireUserResponseDTO.getQuestionnaireId() > 0){
      return ResponseEntity.unprocessableEntity().body("You cannot create a questionnaire with an ID.  Questionnaire ID is an auto generated field.");
    } else {
      Questionnaire questionnaire = modelMapper.map(questionnaireUserResponseDTO, Questionnaire.class);
      return ResponseEntity.ok(
          modelMapper.map(questionnaireService.save(questionnaire), QuestionnaireDTO.class)
      );
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<QuestionnaireDTO> update(@PathVariable Long id, @Valid @RequestBody QuestionnaireUserResponseDTO questionnaireUserResponseDTO) {
    Questionnaire questionnaire = questionnaireService.save(modelMapper.map(questionnaireUserResponseDTO, Questionnaire.class));
    return ResponseEntity.ok(
        modelMapper.map(questionnaire, QuestionnaireDTO.class)
    );
  }

  @DeleteMapping("/{id}")
  public ResponseEntity delete(@PathVariable Long id) {
    questionnaireService.deleteById(id);
    return ResponseEntity.ok().build();
  }

}
