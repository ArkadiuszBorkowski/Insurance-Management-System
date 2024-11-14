package pl.borkowskiarkadiusz.insurancemanagementsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Configuring application security.
 */

@Configuration
public class SecurityConfig {
    private static final String SUPERVISORS_ROLE = "SUPERVISORS";
    private static final String ADJUSTER_ROLE = "CLAIMS_ADJUSTER";
    private static final String ADMIN_ROLE = "ADMIN";


    /**
     * Configures the security filter chain.
     *
     * @param http the HttpSecurity object to configure security
     * @return the configured security filter chain
     * @throws Exception in case of configuration errors
     */

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> requests
                .requestMatchers("/products/config").hasAnyRole(ADMIN_ROLE)
                .anyRequest()
                .authenticated());
        http.formLogin(login -> login
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
        );
        http.logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout/**", HttpMethod.GET.name()))
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
        );
        http.csrf().ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"));
        http.headers().frameOptions().sameOrigin();
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    /**
     * Configures security ignoring for specific resources.
     *
     * @return the WebSecurityCustomizer object to configure security ignoring
     */

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                "/img/**",
                "/scripts/**",
                "/styles/**"
        );
    }
}