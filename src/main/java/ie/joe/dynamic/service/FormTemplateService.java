package ie.joe.dynamic.service;

import ie.joe.dynamic.dao.FormTemplateRepository;
import ie.joe.dynamic.model.FormTemplate;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class FormTemplateService {

  private final FormTemplateRepository formTemplateRepository;

  public FormTemplateService(FormTemplateRepository formTemplateRepository) {
    this.formTemplateRepository = formTemplateRepository;
  }

  public List<FormTemplate> findAll(){
    return formTemplateRepository.findAll();
  }

  public Optional<FormTemplate> findById(long id){
    return formTemplateRepository.findById(id);
  }

  public FormTemplate save( FormTemplate formTemplate) {
    FormTemplate returnFormTemplate = formTemplateRepository.save(formTemplate);
    formTemplateRepository.refresh(returnFormTemplate);
    return returnFormTemplate;
  }

  public void deleteById(Long id) {
    formTemplateRepository.deleteById(id);
  }
}
