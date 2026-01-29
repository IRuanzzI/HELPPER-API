/*
 * SecurityConfig.java
 * Configuração central do Spring Security
 * Define regras de autenticação, autorização e proteção CSRF
 * Configura filtros JWT e endpoints públicos/protegidos
 */
package br.com.helpper.helpper_api.CONFIG;

import br.com.helpper.helpper_api.SECURITY.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Configuração principal da cadeia de filtros de segurança
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desabilita CSRF (usando JWT)
                .csrf(csrf -> csrf.disable())

                // Configuração CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // Configuração de autorização
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos (sem autenticação)
                        .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()

                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/auth/**").authenticated()
                        .requestMatchers("/api/public/**").permitAll()
                        .requestMatchers("/api/arquivos/**").permitAll()
                        .requestMatchers("/api/swagger-ui/**").permitAll()
                        .requestMatchers("/api/v3/api-docs/**").permitAll()
                        .requestMatchers("/api/h2-console/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/prestador").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/prestador").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/contratante").permitAll()
                        .requestMatchers(HttpMethod.POST ,"/contratante").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
                        .requestMatchers(HttpMethod.GET, "/usuarios/**").permitAll()
                        .requestMatchers("/api/arquivos/usuarios/**").permitAll()
                        .requestMatchers("/arquivos/usuarios/**").permitAll()


                        // Endpoints específicos por role
                        .requestMatchers("/api/prestadores/**").hasAnyRole("PRESTADOR", "CONTRATANTE")
                        .requestMatchers("/api/contratantes/**").hasRole("CONTRATANTE")
                        .requestMatchers("/api/servicos/**").hasAnyRole("PRESTADOR", "CONTRATANTE")

                        // Todos os outros endpoints precisam autenticação
                        .anyRequest().authenticated()
                )

                // Stateless (sem sessão)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Adiciona filtro JWT antes do filtro de autenticação
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // Permite frames (para H2 Console - remover em produção)
        http.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }

    /**
     * Configuração CORS para permitir requisições de diferentes origens
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Permite todas as origens, métodos e headers para testes
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    /**
     * Bean do AuthenticationManager para autenticação
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}