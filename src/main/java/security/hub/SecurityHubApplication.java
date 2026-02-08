package security.hub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SecurityHubApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecurityHubApplication.class, args);
    }
}