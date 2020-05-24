package ie.joe.dynamic.controller;

import ie.joe.dynamic.constants.ApiVersion;
import ie.joe.dynamic.dto.FormTemplateDTO;
import ie.joe.dynamic.model.FormTemplate;
import ie.joe.dynamic.service.FormTemplateService;
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
public class FormTemplateController {

  private final FormTemplateService formTemplateService;
  private final ModelMapper modelMapper;

  public FormTemplateController(FormTemplateService formTemplateService, ModelMapper modelMapper) {
    this.formTemplateService = formTemplateService;
    this.modelMapper = modelMapper;
  }

  @GetMapping
  public ResponseEntity<List<FormTemplateDTO>> findAll(){
    return ResponseEntity.ok(
        formTemplateService.findAll().stream()
        .map(entity -> modelMapper.map(entity, FormTemplateDTO.class))
        .collect(Collectors.toList())
      );
  }

  @GetMapping("/{id}")
  public ResponseEntity<FormTemplateDTO> findById(@PathVariable long id){
    Optional<FormTemplate> formTemplate = formTemplateService.findById(id);
    if (!formTemplate.isPresent()) {
      throw new NoSuchElementException("Unable to find Form Template with id: [" + id + "]");
    }
    return ResponseEntity.ok(
        modelMapper.map(formTemplate.get(), FormTemplateDTO.class)
    );
  }

  @PostMapping
  public ResponseEntity create(@Valid @RequestBody FormTemplateDTO formTemplateDTO) {
    if(formTemplateDTO.getTemplateId() > 0){
      return ResponseEntity.unprocessableEntity().body("You cannot create a template with an ID.  Template ID is an auto generated field.");
    } else {
      return ResponseEntity.ok(
          modelMapper.map(
              formTemplateService.save(modelMapper.map(formTemplateDTO, FormTemplate.class)),
              FormTemplateDTO.class)
      );
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<FormTemplateDTO> update(@PathVariable Long id, @Valid @RequestBody FormTemplateDTO formTemplateDTO) {
    return ResponseEntity.ok(
        modelMapper.map(
            formTemplateService.save(modelMapper.map(formTemplateDTO, FormTemplate.class)),
            FormTemplateDTO.class)
    );
  }

  @DeleteMapping("/{id}")
  public ResponseEntity delete(@PathVariable Long id) {
    formTemplateService.deleteById(id);
    return ResponseEntity.ok().build();
  }
}
