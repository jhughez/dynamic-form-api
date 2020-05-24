package ie.joe.dynamic.config;

import ie.joe.dynamic.dao.jpa.JpaRefreshRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "ie.joe.dynamic", repositoryBaseClass = JpaRefreshRepositoryImpl.class)
public class JpaConfiguration {

}
