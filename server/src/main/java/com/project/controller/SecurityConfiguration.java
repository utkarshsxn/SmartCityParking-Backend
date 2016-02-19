//package com.project.controller;
//
//import org.springframework.boot.autoconfigure.security.SecurityProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.web.savedrequest.NullRequestCache;
//import org.springframework.session.web.http.HeaderHttpSessionStrategy;
//import org.springframework.session.web.http.HttpSessionStrategy;
//
///**
// * Created by kaustubh on 11/29/15.
// */
//
//@Configuration
//@EnableWebSecurity
//@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
//class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
//        builder.inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http    .csrf().disable()
//                .authorizeRequests().antMatchers("/MobileSensorCloud/hello").permitAll().and()
//                .authorizeRequests().antMatchers("/MobileSensorCloud/parking").permitAll()
//                //.anyRequest().authenticated()
//                .and()
//                .requestCache()
//                .requestCache(new NullRequestCache())
//                .and()
//                .httpBasic();
//    }
//
//    @Bean
//    public HttpSessionStrategy httpSessionStrategy() {
//        return new HeaderHttpSessionStrategy();
//    }
//}