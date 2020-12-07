package ie.joe.dynamic.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface JpaRefreshRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
  void refresh(T t);
}
