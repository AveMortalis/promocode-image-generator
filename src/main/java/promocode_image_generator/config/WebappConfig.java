package promocode_image_generator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
public class WebappConfig {

    @Value("${my.default.username}")
    private String userName;
    @Value("${my.default.password}")
    private String password;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated()
                )
                .csrf((csrf)-> csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());

        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username(userName)
                .password(password)
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(userDetails);
    }
}
