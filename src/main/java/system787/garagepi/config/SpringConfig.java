package system787.garagepi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import system787.garagepi.pi4j.Pi4JController;

@Configuration
public class SpringConfig {

    @Bean
    public Pi4JController providePi4JController() {
        return new Pi4JController();
    }
}
