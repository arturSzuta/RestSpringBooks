package pl.sda.restspringbooks.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
        return httpSecurity
                .httpBasic()
                .realmName("Books App")
                .and()
                .csrf()
                .disable()
                .headers()
                .and()
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/api/v1/**").hasRole("ADMIN")
                                .anyRequest().permitAll())
                .build();
    }
}
