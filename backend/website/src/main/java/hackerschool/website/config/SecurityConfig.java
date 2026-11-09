package hackerschool.website.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Enables @PreAuthorize annotations in your controllers
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(authorize -> authorize
                        // Publicly allow users to read blog posts
                        .requestMatchers(HttpMethod.GET, "/api/blogposts/**").permitAll()
                        .requestMatchers("/login/**", "/oauth2/**").permitAll()

                        .anyRequest().authenticated()
                )
                // Enable OAuth2 login support for FenixEdu
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("/api/blogposts", true)
                );

        return http.build();
    }
}