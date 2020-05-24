package ie.joe.dynamic.controller;

import ie.joe.dynamic.constants.ApiVersion;
import ie.joe.dynamic.dto.FormDTO;
import ie.joe.dynamic.dto.FormUserResponseDTO;
import ie.joe.dynamic.model.Form;
import ie.joe.dynamic.service.FormService;
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
@RequestMapping("/api/" + ApiVersion.CURRENT + "/forms")
@Log4j2
public class FormController {

  private final FormService formService;
  private final ModelMapper modelMapper;

  public FormController(FormService formService, ModelMapper modelMapper){
    this.formService = formService;
    this.modelMapper = modelMapper;
  }

  @GetMapping
  public ResponseEntity<List<FormDTO>> findAll(){
    return ResponseEntity.ok(
        formService.findAll().stream()
            .map(entity -> modelMapper.map(entity, FormDTO.class))
            .collect(Collectors.toList())
    );
  }

  @GetMapping("/{id}")
  public ResponseEntity<FormDTO> findById(@PathVariable long id){
    Optional<Form> form = formService.findById(id);
    if (!form.isPresent()) {
      throw new NoSuchElementException("Unable to find form with id: [" + id + "]");
    }
    return ResponseEntity.ok(
        modelMapper.map(form.get(), FormDTO.class)
    );
  }

  @GetMapping("byInsp/{inspId}")
  public ResponseEntity<FormDTO> findByInspId(@PathVariable long inspId){
    Optional<Form> form = formService.findByInspId(inspId);
    if (!form.isPresent()) {
      throw new NoSuchElementException("Unable to find Form with inspId: [" + inspId + "]");
    }
    return ResponseEntity.ok(
        modelMapper.map(form.get(), FormDTO.class)
    );
  }

  @PostMapping
  public ResponseEntity create(@Valid @RequestBody FormUserResponseDTO formUserResponseDTO) {
    if(formUserResponseDTO.getFormId() > 0){
      return ResponseEntity.unprocessableEntity().body("You cannot create a form with an ID.  Form ID is an auto generated field.");
    } else {
      Form form = modelMapper.map(formUserResponseDTO, Form.class);
      return ResponseEntity.ok(
          modelMapper.map(formService.save(form), FormDTO.class)
      );
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<FormDTO> update(@PathVariable Long id, @Valid @RequestBody FormUserResponseDTO formUserResponseDTO) {
    Form form = formService.save(modelMapper.map(formUserResponseDTO, Form.class));
    return ResponseEntity.ok(
        modelMapper.map(form, FormDTO.class)
    );
  }

  @DeleteMapping("/{id}")
  public ResponseEntity delete(@PathVariable Long id) {
    formService.deleteById(id);
    return ResponseEntity.ok().build();
  }

}
