package com.bugimarket.security.config;
import com.bugimarket.security.filter.JWTAuthenticationFilter;
import com.bugimarket.security.filter.LoginFilter;
import com.bugimarket.security.provider.JWTAuthenticationProvider;
import com.bugimarket.security.provider.JwtTokenProvider;
import com.bugimarket.security.provider.LoginAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationConfiguration authenticationConfiguration;


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth,
                                LoginAuthenticationProvider loginAuthenticationProvider,
                                JWTAuthenticationProvider jwtAuthenticationProvider){
        auth
                .authenticationProvider(loginAuthenticationProvider)
                .authenticationProvider(jwtAuthenticationProvider);
    }



    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable);

        http
                .formLogin(AbstractHttpConfigurer::disable);

        http
                .httpBasic(AbstractHttpConfigurer::disable);

        http
                .authorizeHttpRequests((auth) -> auth
                                .requestMatchers(HttpMethod.POST,"users").permitAll()
                                .requestMatchers("/login","/").permitAll()
                        .anyRequest().authenticated());

        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        AuthenticationManager authenticationManager = authenticationManager(authenticationConfiguration);
        http
                .addFilterAt(new LoginFilter(authenticationManager, jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        http
                .addFilterAfter(new JWTAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);



        return http.build();
    }


}