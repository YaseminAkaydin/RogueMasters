package de.roguemaster.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    String URL = "http://localhost";
    int PORT = 8812;

    /**
     * Configures the security filter chain for the application. This method sets up CORS and CSRF configurations,
     * session management policies, and HTTP request authorizations.
     *
     * @param http The HttpSecurity to configure.
     * @return A SecurityFilterChain object configured with security settings.
     * @throws Exception If an error occurs during the configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/enemy/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                )
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/enemy/*").permitAll();
                    auth.anyRequest().authenticated();
                })
                .build();
    }

    /**
     * Defines a CORS configuration source for the application. Configures allowed origins, methods, and
     * other CORS settings.
     *
     * @return A CorsConfigurationSource object with CORS configurations.
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(URL + ":" + PORT));
        configuration.setAllowedMethods(Arrays.asList("DELETE","POST"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
