//package com.skwarek.blog.configuration;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//import javax.sql.DataSource;
//
///**
// * Created by Michal on 04.01.2017.
// */
//@Configuration
//@EnableWebSecurity
//public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {
//
////    @Autowired
////    private DataSource dataSource;
//
////    @Autowired
////    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
////
////        auth.jdbcAuthentication().dataSource(dataSource)
////                .usersByUsernameQuery(
////                        "select username, password, enabled from user where username=?")
////                .authoritiesByUsernameQuery(
////                        "select username, role from user where username=?");
////    }
//
//    @Autowired
//    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("michal").password("123").roles("ADMIN");
//        auth.inMemoryAuthentication().withUser("radek").password("123").roles("ADMIN");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http.authorizeRequests()
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .and()
//                .formLogin().loginPage("/accounts/login").defaultSuccessUrl("/").failureUrl("/accounts/login?error")
//                .usernameParameter("username").passwordParameter("password")
//                .and()
//                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/accounts/logout")).logoutSuccessUrl("/")
//                .and()
//                .csrf();
//    }
//}