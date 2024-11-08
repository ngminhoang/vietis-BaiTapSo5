package com.nqt.cs3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SpringSecurityConfig {

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationSuccessHandler customSuccessHandler() {
                return new CustomSuccessHandler();
        }

        @Bean
        public DaoAuthenticationProvider authProvider(
                PasswordEncoder passwordEncoder,
                UserDetailsService userDetailsService) {
                DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
                authProvider.setUserDetailsService(userDetailsService);
                authProvider.setPasswordEncoder(passwordEncoder);
                return authProvider;
        }

        @Bean
        public SpringSessionRememberMeServices rememberMeServices() {
                SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
                rememberMeServices.setAlwaysRemember(true);
                return rememberMeServices;
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                        .csrf(csrf -> csrf.disable())
                        .authorizeHttpRequests(authorize -> authorize
                                .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.INCLUDE)
                                .permitAll()
                                .requestMatchers("/", "/css/**", "/js/**", "/images/**", "/assets/**", "/register/**", "/detail-course/**")
                                .permitAll()
                                .requestMatchers("/student/**", "/course/**", "/admin/**").hasRole("ADMIN")
                                .requestMatchers("/enrollment/**").hasRole("USER")
                                .anyRequest().authenticated()
                        )
                        .formLogin(formLogin -> formLogin
                                .loginPage("/login")
                                .successHandler(customSuccessHandler())
                                .failureUrl("/erorr")
                                .permitAll()
                        )                
                        .sessionManagement((sessionManagement) -> sessionManagement
                                                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                                                .invalidSessionUrl("/login")
                                                .maximumSessions(1)
                                                .maxSessionsPreventsLogin(false)
                        )
                        .rememberMe(rememberMe -> rememberMe
                                                .rememberMeServices(rememberMeServices())
                        )
                        .logout(logout -> logout
                                .logoutSuccessUrl("/index")
                                .logoutUrl("/logout")
                                .deleteCookies("JSESSIONID").invalidateHttpSession(true)
                        );  
                return http.build();
        }
}
