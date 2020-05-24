package ie.joe.dynamic.dao;

import ie.joe.dynamic.model.ValidationRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValidationRuleRepository extends JpaRepository<ValidationRule, Long> {
}
