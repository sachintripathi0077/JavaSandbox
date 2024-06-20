package com.eazybytes.eazyschool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf)->
                csrf
                        .disable()
        );

        // permits all requests to the web application
//        http.authorizeHttpRequests((requests) -> {
//            ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)requests.anyRequest()).permitAll();
//        });
//        // denies all requests to the web application
//        http.authorizeHttpRequests((requests) -> {
//            ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)requests.anyRequest()).denyAll();
//        });
        http.authorizeHttpRequests((requests) ->
            requests
                    .requestMatchers("/dashboard").authenticated()
                    .requestMatchers("/home","/").permitAll()
                    .requestMatchers("/holidays/**").permitAll()
                    .requestMatchers("/contact").permitAll()
                    .requestMatchers("saveMsg").permitAll()
                    .requestMatchers("/courses").permitAll()
                    .requestMatchers("/about").permitAll()
                    .requestMatchers("/assets/**").permitAll()
                    .requestMatchers("/login").permitAll()
            );

        http.formLogin((form)->
                form
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard")
                        .failureUrl("/login?error=true").permitAll()
        );

        http.logout((logout)->
                logout
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .permitAll()
        );
        http.httpBasic(Customizer.withDefaults());
        return (SecurityFilterChain)http.build();
    }

    // adding temporary user and details
    @Bean
    public InMemoryUserDetailsManager userDetailsService(){
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("123")
                .roles("USER","ADMIN")
                .build();
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("321")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin,user);
    }
}
