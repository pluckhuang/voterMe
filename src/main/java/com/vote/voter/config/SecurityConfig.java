package com.vote.voter.config;

import javax.sql.DataSource;

import com.vote.voter.filter.CsrfTokenGeneratorFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select username,password,enabled from t_user where username =?")
                .authoritiesByUsernameQuery("select username,authority from t_authority where username = ?")
                .passwordEncoder(passwordEncoder());
    }

    // @Autowired
    // public void initialize(AuthenticationManagerBuilder builder, DataSource
    // dataSource) throws Exception {
    // builder.jdbcAuthentication().dataSource(dataSource)
    // .usersByUsernameQuery("select name,password,enabled from t_user where name =
    // ?")
    // .authoritiesByUsernameQuery("select name, authority " + "from t_authority
    // where name=?")
    // .passwordEncoder(new BCryptPasswordEncoder(16));
    // }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAfter(new CsrfTokenGeneratorFilter(), CsrfFilter.class).authorizeRequests()
                .antMatchers("/voter/play1").permitAll().antMatchers("/voter/display")
                .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN").antMatchers("/actuator/**").permitAll().anyRequest()
                .authenticated().and().formLogin().loginProcessingUrl("/index.html")
                .defaultSuccessUrl("/index.html", true).and().logout();

        // http.headers().addHeaderWriter(new
        // StaticHeadersWriter("Content-Security-Policy",
        // "default-src 'none'; script-src "));

        // http.csrf().disable().authorizeRequests().antMatchers("/voter/display").hasAuthority("ROLE_USER")
        // .antMatchers("/actuator/**").permitAll().anyRequest().authenticated().and().formLogin()
        // .loginProcessingUrl("/web/vote_display.html");
        // .antMatchers("/actuator").permitAll()
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
