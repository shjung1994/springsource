package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    
    @Bean
    SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
        
        http
            .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/","/sample/guest").permitAll()
            .requestMatchers("/sample/member").hasRole("USER")
            .requestMatchers("/sample/admin").hasRole("ADMIN")
            .anyRequest().authenticated())
            // .httpBasic(Customizer.withDefaults());
            // .formLogin(Customizer.withDefaults()); // 시큐리티가 제공하는 기본 로그인 폼 페이지
            .formLogin(login -> login.loginPage("/member/login").permitAll());
        http.logout(logout -> logout
            .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
            .logoutSuccessUrl("/"));

        return http.build();
    }
    
    @Bean // = new 한 후 스프링 컨테이너가 관리해 줘!
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
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
