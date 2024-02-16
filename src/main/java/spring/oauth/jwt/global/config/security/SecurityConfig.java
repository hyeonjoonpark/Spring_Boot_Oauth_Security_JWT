package spring.oauth.jwt.global.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import spring.oauth.jwt.domain.auth.service.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private final CustomOAuth2UserService oAuth2UserService;
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf(AbstractHttpConfigurer::disable)
      .cors(Customizer.withDefaults())
      .formLogin(AbstractHttpConfigurer::disable)
      .httpBasic(AbstractHttpConfigurer::disable)
      // CustomOAuth2UserSerivce 등록
      .oauth2Login(oAuth2 -> oAuth2.userInfoEndpoint(
        userInfoEndpointConfig ->
          userInfoEndpointConfig.userService(oAuth2UserService))
      )
      .authorizeHttpRequests(
        (auth) -> auth
        .requestMatchers("/").permitAll()
        .anyRequest().authenticated()
      )
      .sessionManagement(
        (session) -> session
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      );

    return http.build();
  }
}
