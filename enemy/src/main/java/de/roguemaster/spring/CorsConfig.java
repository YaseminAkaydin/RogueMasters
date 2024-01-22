package de.roguemaster.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class CorsConfig {
    String URL = "http://localhost";
    int PORT = 8812;

    /**
     * Configures Cross-Origin Resource Sharing (CORS) for the application. This bean defines specific
     * CORS mappings, including allowed origins, methods, credentials, and other settings.
     *
     * @return A WebMvcConfigurer object with CORS configurations.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer () {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/enemy/**") // Adjust the path as needed
                        .allowedOrigins(URL + ":" + PORT) // Allow requests from React's development server
                        .allowedMethods("POST", "GET", "DELETE") // Allow specific HTTP methods
                        .allowCredentials(true) // Allow cookies and credentials if needed
                        .maxAge(3600);
            }
        };
    }

    /**
     * Creates a CORS filter for handling CORS requests based on the specified URL patterns and configurations.
     * Configures allowed origins, headers, methods, and credentials.
     *
     * @return A CorsFilter object configured with CORS settings.
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin(URL + ":" + PORT);  // TODO: lock down before deploying
        config.addAllowedHeader("*");
        config.addExposedHeader(HttpHeaders.AUTHORIZATION);
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
