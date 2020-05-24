package ie.joe.dynamic.dao;

import ie.joe.dynamic.model.ValidationLink;
import ie.joe.dynamic.model.ValidationLink.ValidationLinkPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValidationLinkRepository extends JpaRepository<ValidationLink, ValidationLinkPK> {
}
