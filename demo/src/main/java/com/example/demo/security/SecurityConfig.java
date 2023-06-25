package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;



@EnableWebSecurity
@Configuration
public class SecurityConfig {
    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //     http.formLogin(login -> login
    //         .loginProcessingUrl("/login")
    //         .loginPage("/login")
    //         .defaultSuccessUrl("/")
    //         .failureUrl("/login?error")
    //         .permitAll()
    //     ).logout(logout -> logout
    //         .logoutSuccessUrl("/login")
    //     ).authorizeHttpRequests(authz -> authz
    //         .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
    //             .permitAll()
    //         .requestMatchers("/")
    //             .permitAll()
    //         .anyRequest().authenticated()
    //     );
    //     return http.build();
    // }

    @Bean
    public PasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    // @Bean
    // public UserDetailsService users() {
    //     UserDetails user = User.builder()
    //                             .username("testuser")
    //                             .password(passwordEncoder().encode("tokyo123"))
    //                             .authorities("USER")
    //                             .build();
    //     System.out.println(passwordEncoder().encode("tokyo123"));
    //     return new InMemoryUserDetailsManager(user);
    // }

}
