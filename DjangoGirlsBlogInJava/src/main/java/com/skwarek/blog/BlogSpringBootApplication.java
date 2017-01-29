package com.skwarek.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.sql.DataSource;

/**
 * Created by Michal on 16.09.2016.
 */
@SpringBootApplication
public class BlogSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogSpringBootApplication.class, args);
    }

    @Configuration
    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Autowired
        private DataSource dataSource;

        @Autowired
        public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

            auth.jdbcAuthentication().dataSource(dataSource)
                    .usersByUsernameQuery(
                            "select username, password, enabled from user where username=?")
                    .authoritiesByUsernameQuery(
                            "select username, role from user where username=?");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .httpBasic()
                    .and()
                    .authorizeRequests()
                    .antMatchers("/views/index.html",
                            "/views/blog/post_list.html",
                            "/views/registration/login.html",
                            "/",
                            "/posts",
                            "/accounts/login",
                            "/logout",
                            "/drafts"
                    ).permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .csrf()
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        }
    }
}
