package server.plagiarism;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
  * @author Rahul Verma [verma.rah@husky.neu.edu]
 * @version 1.0
 *
 * Class
 * Application: the main Spring Boot root app class which executes the run method
 */
@SpringBootApplication
public class Application {

    // Logger (slf4j) variable for logging events
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    /**
     * Method (static class method)
     * main: main method of the spring boot application which executes the run method
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("Application started");
    }
}
