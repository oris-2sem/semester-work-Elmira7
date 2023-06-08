package ru.itis.sem_1.security.config;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.itis.sem_1.security.filter.TokenAuthenticationFilter;
import ru.itis.sem_1.security.filter.TokenAuthorizationFilter;
import ru.itis.sem_1.security.handler.EmailAuthenticationSuccessHandler;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   TokenAuthenticationFilter tokenAuthenticationFilter,
                                                   TokenAuthorizationFilter tokenAuthorizationFilter,
                                                   EmailAuthenticationSuccessHandler emailAuthenticationSuccessHandler) throws Exception {
        return httpSecurity
                .authorizeRequests()
                    .antMatchers("/user/login", "/user/register").not().authenticated()
                    .antMatchers("/user/profile/**").authenticated()
                .antMatchers("/").permitAll()
                .and()
                .csrf().disable()
                //.addFilter(tokenAuthenticationFilter)
                //.addFilterBefore(tokenAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .successHandler(emailAuthenticationSuccessHandler)
                .loginPage("/user/login")
                .usernameParameter("email")
                .loginProcessingUrl("/user/login/perform")
                .defaultSuccessUrl("/user/profile")
                .failureUrl("/user/login?error=true")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .and()
                .build();
    }

    @Autowired
    public void injectAccountDetailAndPasswordEncode(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }


}
