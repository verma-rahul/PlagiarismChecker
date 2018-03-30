package server.plagiarism.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.stereotype.Component;

/**
 * @author Rahul Verma [verma.rah@husky.neu.edu]
 * @version 1.0
 *
 * Class
 * CustomContainer: used to configure the embedded servlet container
 */
@Component
public class CustomContainer implements EmbeddedServletContainerCustomizer {

    /**
     * Method (overridden)
     * customize: customize the properties of container
     * @param container: Configurable servlet container instance
     */
    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        container.setContextPath("/api");
    }
}
