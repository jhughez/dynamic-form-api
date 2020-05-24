package ie.joe.dynamic.controller;

import ie.joe.dynamic.constants.ApiVersion;
import ie.joe.dynamic.dto.QuestionnaireTemplateDTO;
import ie.joe.dynamic.model.QuestionnaireTemplate;
import ie.joe.dynamic.service.QuestionnaireTemplateService;
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
@RequestMapping("/api/" + ApiVersion.CURRENT + "/templates")
@Log4j2
public class QuestionnaireTemplateController {

  private final QuestionnaireTemplateService questionnaireTemplateService;
  private final ModelMapper modelMapper;

  public QuestionnaireTemplateController(QuestionnaireTemplateService questionnaireTemplateService, ModelMapper modelMapper) {
    this.questionnaireTemplateService = questionnaireTemplateService;
    this.modelMapper = modelMapper;
  }

  @GetMapping
  public ResponseEntity<List<QuestionnaireTemplateDTO>> findAll(){
    return ResponseEntity.ok(
        questionnaireTemplateService.findAll().stream()
        .map(entity -> modelMapper.map(entity, QuestionnaireTemplateDTO.class))
        .collect(Collectors.toList())
      );
  }

  @GetMapping("/{id}")
  public ResponseEntity<QuestionnaireTemplateDTO> findById(@PathVariable long id){
    Optional<QuestionnaireTemplate> questionnaireTemplate = questionnaireTemplateService.findById(id);
    if (!questionnaireTemplate.isPresent()) {
      throw new NoSuchElementException("Unable to find Questionnaire Template with id: [" + id + "]");
    }
    return ResponseEntity.ok(
        modelMapper.map(questionnaireTemplate.get(), QuestionnaireTemplateDTO.class)
    );
  }

  @PostMapping
  public ResponseEntity create(@Valid @RequestBody QuestionnaireTemplateDTO questionnaireTemplateDTO) {
    if(questionnaireTemplateDTO.getTemplateId() > 0){
      return ResponseEntity.unprocessableEntity().body("You cannot create a template with an ID.  Template ID is an auto generated field.");
    } else {
      return ResponseEntity.ok(
          modelMapper.map(
              questionnaireTemplateService.save(modelMapper.map(questionnaireTemplateDTO, QuestionnaireTemplate.class)),
              QuestionnaireTemplateDTO.class)
      );
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<QuestionnaireTemplateDTO> update(@PathVariable Long id, @Valid @RequestBody QuestionnaireTemplateDTO questionnaireTemplateDTO) {
    return ResponseEntity.ok(
        modelMapper.map(
            questionnaireTemplateService.save(modelMapper.map(questionnaireTemplateDTO, QuestionnaireTemplate.class)),
            QuestionnaireTemplateDTO.class)
    );
  }

  @DeleteMapping("/{id}")
  public ResponseEntity delete(@PathVariable Long id) {
    questionnaireTemplateService.deleteById(id);
    return ResponseEntity.ok().build();
  }
}
