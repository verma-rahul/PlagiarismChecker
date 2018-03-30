package server.plagiarism.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @author Rahul Verma [verma.rah@husky.neu.edu]
 * @version 1.0
 *
 * Class
 * WebConfig: used tp configure spring mvc configurations
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    /**
     * Method (overridden)
     * addCorsMappings: configure the CORS Support for All types of request for the Spring Boot Application
     * @param registry: CorsRegistry object
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD");
    }

}
