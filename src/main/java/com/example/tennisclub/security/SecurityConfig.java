package com.example.tennisclub.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails admin = User.withUsername("admin")
                .password("admin123")
                .roles("ADMIN")
                .build();

        UserDetails membre = User.withUsername("membre")
                .password("membre123")
                .roles("MEMBRE")
                .build();

        return new InMemoryUserDetailsManager(admin, membre);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // H2-console + formulaires => on désactive CSRF pour simplifier le TP
                .csrf(csrf -> csrf.disable())


                // Autorisations
                .authorizeHttpRequests(auth -> auth
                        // Public (pas besoin de login)
                        .requestMatchers("/", "/login", "/css/**", "/js/**", "/images**").permitAll()

                        // H2 console
                        .requestMatchers("/h2-console/**").permitAll()

                        // Admin only
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // API: login obligatoire
                        .requestMatchers("/api/**").authenticated()

                        // Tout le reste nécessite connexion
                        .anyRequest().authenticated()
                )

                // Login form
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )

                // Logout
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )

                // utile
                .httpBasic(Customizer.withDefaults());

        // IMPORTANT pour H2-console (sinon page blanche / blocked)
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }

    // TP only (pas sécurisé) : passwords en clair
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
