package pl.sda.restspringbooks.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(jsr250Enabled = true, securedEnabled = true)
public class SecurityConfiguration extends GlobalMethodSecurityConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic()
                .realmName("Books App")
                .and()
                .csrf()
                .disable()
                .headers()
                .and()
                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers(HttpMethod.PUT, "/api/v1/books/").hasAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.GET, "/api/v1/***").hasAuthority("ROLE_USER")
//                                .requestMatchers("/api/v1/**").hasRole("ADMIN")
                                .anyRequest().permitAll())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        final UserDetails paul = User
                .builder()
                .password(passwordEncoder().encode("1234"))
                .username("paul")
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .authorities("ROLE_ADMIN", "ROLE_USER")
                .build();
        final UserDetails karol = User
                .builder()
                .password(passwordEncoder().encode("abcd"))
                .username("karol")
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .authorities("ROLE_USER")
                .build();
        return new InMemoryUserDetailsManager(paul, karol);
    }
}
