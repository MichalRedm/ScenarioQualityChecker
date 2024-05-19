package pl.put.poznan.qualitychecker.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.put.poznan.qualitychecker.logic.Scenario;

/**
 * Main class of the project.
 * Runs the Spring application.
 */
@SpringBootApplication(scanBasePackages = {"pl.put.poznan.qualitychecker.rest"})
public class ScenarioQualityCheckerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScenarioQualityCheckerApplication.class, args);
    }
}
