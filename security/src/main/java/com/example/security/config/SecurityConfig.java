package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices.RememberMeTokenAlgorithm;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.security.security.CustomLoginSuccessHandler;

@EnableMethodSecurity // @PreAuthorize, @PostAuthorize를 쓸거야 라는 소리임
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    
    @Bean
    SecurityFilterChain SecurityFilterChain(HttpSecurity http, RememberMeServices rememberMeServices) throws Exception {
        
        http
            // .authorizeHttpRequests(authorize -> authorize
            // .requestMatchers("/","/sample/guest").permitAll()
            // .requestMatchers("/sample/member").hasRole("USER")
            // .requestMatchers("/sample/admin").hasRole("ADMIN")
            // .anyRequest().authenticated())
            // .httpBasic(Customizer.withDefaults());
            // .formLogin(Customizer.withDefaults()); // 시큐리티가 제공하는 기본 로그인 폼 페이지
            .authorizeHttpRequests(authorize -> authorize.requestMatchers("/css/**","/js/**","/image/**").permitAll()
            .anyRequest().permitAll())
            .formLogin(login -> login.loginPage("/member/login").permitAll()
            .successHandler(successHandler())
            .permitAll())
            .oauth2Login(login -> login.successHandler(successHandler()));
        http.logout(logout -> logout
            .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
            .logoutSuccessUrl("/"));
            
        http.rememberMe(remember -> remember.rememberMeServices(rememberMeServices));    

        return http.build();
    }
    
    @Bean // = new 한 후 스프링 컨테이너가 관리해 줘!
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    CustomLoginSuccessHandler successHandler() {
        return new CustomLoginSuccessHandler();
    }

    @Bean
    RememberMeServices rememberMeServices(UserDetailsService userDetailsService) {
        RememberMeTokenAlgorithm encodingAlgorithm = RememberMeTokenAlgorithm.SHA256;
        TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices("myKey",
        userDetailsService, encodingAlgorithm);
        rememberMeServices.setMatchingAlgorithm(RememberMeTokenAlgorithm.MD5);
        rememberMeServices.setTokenValiditySeconds(60 * 60 * 24 * 7);
        return rememberMeServices;
    }

    // dbeaver 없을 때 사용했던 것
    // @Bean
    // UserDetailsService users() {
    //     UserDetails user = User.builder()
    //     .username("user")
    //     .password("{bcrypt}$2a$10$Y.01CxE3OI9XHwF6nKhYWeRVLJabcUfQEHHLOmd1p2wP9PtxztSIy")
    //     .roles("USER") // ROLE_권한명, 여기선 ROLE_USER
    //     .build();

    //     UserDetails admin = User.builder()
    //     .username("admin")
    //     .password("{bcrypt}$2a$10$Y.01CxE3OI9XHwF6nKhYWeRVLJabcUfQEHHLOmd1p2wP9PtxztSIy")
    //     .roles("USER","ADMIN") // ROLE_권한명, 여기선 ROLE_USER
    //     .build();
    //     return new InMemoryUserDetailsManager(user, admin);
    // }

}
