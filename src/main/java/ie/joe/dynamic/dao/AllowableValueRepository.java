package ie.joe.dynamic.dao;

import ie.joe.dynamic.model.AllowableValue;
import ie.joe.dynamic.model.AllowableValue.AllowableValuePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllowableValueRepository extends JpaRepository<AllowableValue, AllowableValuePK> {
}
