package example.hellosecurity.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/**")
                .authorizeHttpRequests(
                    auth -> auth.requestMatchers("/public-data/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/private-data/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .logout(Customizer.withDefaults()).exceptionHandling().accessDeniedPage("/noaccess.html");
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder().username("admin").password("{noop}qwerty").roles("ADMIN").build();
        UserDetails lamer = User.builder().username("user1").password("{noop}1234").roles("USER").build();

        return new InMemoryUserDetailsManager(admin, lamer);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
