package ie.joe.dynamic.dao;

import ie.joe.dynamic.dao.jpa.JpaRefreshRepository;
import ie.joe.dynamic.model.Questionnaire;

public interface QuestionnaireRepository extends JpaRefreshRepository<Questionnaire, Long> {
}
