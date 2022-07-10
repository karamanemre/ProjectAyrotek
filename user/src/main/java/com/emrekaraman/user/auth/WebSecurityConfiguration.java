package com.emrekaraman.user.auth;

import com.emrekaraman.user.business.dtos.LoginRequestDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailService userDetailsService;
    private final AuthenticationManagerBuilder builder;
    private final JwtTokenFilter jwtTokenFilter;

    @Lazy
    public WebSecurityConfiguration(UserDetailService userDetailsService, AuthenticationManagerBuilder builder, JwtTokenFilter jwtTokenFilter) {
        this.userDetailsService = userDetailsService;
        this.builder = builder;
        this.jwtTokenFilter = jwtTokenFilter;
    }


    public Authentication authenticationManager(LoginRequestDto loginRequestDto) throws Exception {
        return super.authenticationManagerBean().authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(),loginRequestDto.getPassword()));
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http
                .exceptionHandling()
                .authenticationEntryPoint(new AuthEntryPoint());

        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/api/userws/getAll").authenticated()
                .and()
                .authorizeRequests()
                .anyRequest()
                .permitAll();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(getBCryptPasswordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
}
