package iot.project.IoTProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class IoTProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(IoTProjectApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/measurements").allowedOrigins("https://localhost:4200");
                registry.addMapping("/measurements-hardcoded").allowedOrigins("https://localhost:4200");

            }
        };
    }
}
