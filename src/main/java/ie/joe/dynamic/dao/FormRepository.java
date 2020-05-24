package ie.joe.dynamic.dao;

import ie.joe.dynamic.dao.jpa.JpaRefreshRepository;
import ie.joe.dynamic.model.Form;
import java.util.Optional;

public interface FormRepository extends JpaRefreshRepository<Form, Long> {

  Optional<Form> findByInspId(Long inspId);
}
