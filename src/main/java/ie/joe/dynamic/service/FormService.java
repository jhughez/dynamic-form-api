package ie.joe.dynamic.service;

import ie.joe.dynamic.model.Form;
import ie.joe.dynamic.dao.FormRepository;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class FormService {

  private final FormRepository formRepository;

  public FormService(FormRepository formRepository) {
    this.formRepository = formRepository;
  }

  public Optional<Form> findByInspId(long inspId) {
    return formRepository.findByInspId(inspId);
  }

  public Optional<Form> findById(long id) {
    return formRepository.findById(id);
  }

  public List<Form> findAll() {
    return formRepository.findAll();
  }

  public Form save( Form form) {
    Form returnForm = formRepository.save(form);
    formRepository.refresh(returnForm);
    return returnForm;
  }

  public void deleteById(Long id) {
    formRepository.deleteById(id);
  }
}
