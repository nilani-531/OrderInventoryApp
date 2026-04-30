package com.sprintProject.orderinventoryapplication.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoginAuthentication {
    // Global CORS configuration to allow frontend at http://localhost:4200
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:4200")
						.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
						.allowedHeaders("*")
						.allowCredentials(true);
			}
		};
	}



    
   String adminId="ADMIN";
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // Allow Spring Security to process CORS (including OPTIONS preflight)
            // before any authentication checks — delegates to CorsConfig bean
            .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Let OPTIONS preflight requests through without authentication
                .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/api/products/**", "/api/inventory/**")
                    .hasAnyRole("KARTHI", adminId)
                .requestMatchers("/api/customers/**")
                    .hasAnyRole("ABINAYA", adminId)
                .requestMatchers("/api/orders/**", "/api/order-items/**")
                    .hasAnyRole("NILANI", adminId)
                .requestMatchers("/api/stores/**")
                    .hasAnyRole("POOJA", adminId)
                .requestMatchers("/api/shipments/**")
                    .hasAnyRole("YAMINI", adminId)
                .requestMatchers(
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }
    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder encoder) {

        UserDetails karthi = User
                .withUsername("karthi")
                .password(encoder.encode("karthi123"))
                .roles("KARTHI")
                .build();

        UserDetails nilani = User
                .withUsername("nilani")
                .password(encoder.encode("54321N"))
                .roles("NILANI")
                .build();

        UserDetails pooja = User
                .withUsername("pooja")
                .password(encoder.encode("123456"))
                .roles("POOJA")
                .build();

        UserDetails abinaya = User
                .withUsername("abinaya")
                .password(encoder.encode("abi123"))
                .roles("ABINAYA")
                .build();
        
        UserDetails yamini = User
                .withUsername("yamini")
                .password(encoder.encode("yamx123"))
                .roles("YAMINI")
                .build();

        UserDetails admin = User
                .withUsername("admin")
                .password(encoder.encode("admin123"))
                .roles(adminId)
                .build();

        return new InMemoryUserDetailsManager(
                karthi,
                nilani,
                pooja,
                abinaya,
                yamini,
                admin
        );
    }
}

