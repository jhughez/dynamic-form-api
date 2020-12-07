package ie.joe.dynamic.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .components(new Components())
        .info(new Info().title("Dynamic questionnaire API").description(
            "The purpose of this API is to provide and interface to allow a front end to retrieve and update all"
                + " information required to display checklist questionnaires."));
  }
}
