package dev.YanAlmeida.SistemaDeGestaoDePousada.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/pousada/**")
                .allowedOrigins(
                        "http://localhost:3000",
                        "http://localhost:5173",
                        "http://localhost:4200",
                        "http://127.0.0.1:3000",
                        "http://127.0.0.1:5173"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS") // DELETE adicionado
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}