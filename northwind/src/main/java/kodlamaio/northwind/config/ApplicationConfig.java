package kodlamaio.northwind.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Application custom config value
 *
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "ses")
public class ApplicationConfig {

    /**
     * Temporary file directory
     */
    private String tmpDir;

}
