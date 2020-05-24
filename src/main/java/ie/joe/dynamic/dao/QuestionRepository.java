package ie.joe.dynamic.dao;

import ie.joe.dynamic.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
