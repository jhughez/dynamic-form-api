package ie.joe.dynamic;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.WebApplicationInitializer;

@SpringBootApplication
@Log4j2
public class DynamicApplication extends SpringBootServletInitializer implements WebApplicationInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return configureApplication(builder);
  }

  public static void main(String[] args) {
    configureApplication(new SpringApplicationBuilder()).run(args);
  }

  private static SpringApplicationBuilder configureApplication(SpringApplicationBuilder builder) {
    return builder.sources(DynamicApplication.class).bannerMode(Banner.Mode.OFF);
  }
}
