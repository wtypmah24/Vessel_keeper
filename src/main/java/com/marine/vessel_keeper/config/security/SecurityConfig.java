package com.marine.vessel_keeper.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static com.marine.vessel_keeper.entity.user.Role.CREW_MANAGER;
import static com.marine.vessel_keeper.entity.user.Role.OWNER;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain formLoginFilterChain(HttpSecurity http, AuthenticationManager manager) throws Exception {
        return http
                .csrf().disable()
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers("/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/**").hasAuthority(OWNER.name())
                                .requestMatchers(HttpMethod.DELETE, "/users/**").hasAuthority(OWNER.name())
                                .requestMatchers(HttpMethod.GET, "/users/**").hasAuthority(OWNER.name())
                                .requestMatchers(HttpMethod.GET, "/seamen/**").hasAuthority(CREW_MANAGER.name())
                                .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .authenticationManager(manager)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        //return new BCryptPasswordEncoder();
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance(); // пока без шифрования пароля в БД
    }
}