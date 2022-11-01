package com.upskill.rentcars.config;

import com.upskill.rentcars.config.jwt.JwtFilter;
import com.upskill.rentcars.model.db.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.upskill.rentcars.model.db.Role.ROLE_ADMIN;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                //.httpBasic().disable()
                .csrf().disable()
                //.cors()
                //.and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/cars").permitAll()
                .antMatchers(HttpMethod.GET,"/customers").permitAll()
                //.antMatchers("/customers", "/users/**", "/cars").hasRole("ADMIN")

                .antMatchers("/register", "/auth").permitAll()
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
