package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    // @Bean
    // public UserDetailsService users() {
    //     UserDetails user = User.builder()
    //                             .username("testuser")
    //                             .password(passwordEncoder().encode("123456"))
    //                             .authorities("USER")
    //                             .build();
    //     System.out.println(passwordEncoder().encode("123456"));
    //     return new InMemoryUserDetailsManager(user);
    // }

}
