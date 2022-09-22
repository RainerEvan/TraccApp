package com.traccapp.demo.security;

import com.traccapp.demo.security.details.UserDetailsServiceImpl;
import com.traccapp.demo.security.jwt.AuthEntryPoint;
import com.traccapp.demo.security.jwt.AuthTokenFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    
    @Autowired
    private final UserDetailsServiceImpl userDetailsService;
    @Autowired
    private final AuthEntryPoint unauthorizedHandler;

    @Bean
    public AuthTokenFilter authTokenFilter(){
        return new AuthTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
            .httpBasic().disable();
		// 	.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
		// 	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        //     .authorizeRequests()
        //     .antMatchers("/api/auth/**").permitAll()
        //     .antMatchers("/graphql").permitAll()
        //     .antMatchers("/graphiql").permitAll()
        //     .antMatchers("/api/**").permitAll()
        //     .anyRequest().authenticated()
        //     .and()
        //     .logout()
        //         .clearAuthentication(true)
        //         .invalidateHttpSession(true)
        //         .deleteCookies("JSESSIONID")
        //         .permitAll();

        // http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    
}
