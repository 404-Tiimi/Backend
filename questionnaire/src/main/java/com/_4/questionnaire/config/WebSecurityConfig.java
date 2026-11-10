package com._4.questionnaire.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration 
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {
    // Luo salasanojen salaamiseen käytettävän BCrypt-encoderin.
    @Bean 
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Määrittelee sivujen käyttöoikeudet ja kirjautumisen toiminnan.
    @Bean 
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http
			.cors(Customizer.withDefaults())
			.csrf(csrf -> csrf
            .ignoringRequestMatchers("/h2-console/**", "/rest/**"))

			.authorizeHttpRequests( authorize -> authorize 
                // Rekisteröinti ja staattiset tiedostot ovat julkisia.
                .requestMatchers("/auth/login").permitAll() // Public signup and static assets.
                .requestMatchers("/rest/**").permitAll()
				.requestMatchers("/h2-console/**").permitAll()
				.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // Kaikki muut sivut vaativat kirjautumisen.
				.anyRequest().authenticated()                 // All other paths must be authenticated.
			)      

			.headers(headers -> headers
    		.frameOptions(frame -> frame.sameOrigin())
) 
			
			// If you don't give loginPage
        // Määrittelee kirjautumissivun ja onnistuneen kirjautumisen kohteen.
		.formLogin( formlogin -> formlogin                    // your application will use the
			.loginPage("/auth/login")                          // spring boot default login page.
			.loginProcessingUrl("/auth/login")
			.failureUrl("/auth/login?error")
			.defaultSuccessUrl("/", true)      // <-- Tells where to go after
			.permitAll()                                  // successful login.
		)
        // Määrittelee uloskirjautumisen toiminnan.
		.logout( logout -> logout
			.invalidateHttpSession(true)
            .logoutSuccessUrl("/auth/login?logout")
			.permitAll()
		);
		return http.build();
	}

	// Määrittelee, mistä frontendistä REST-pyyntöjä saa lähettää.
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of("http://localhost:5173"));
		configuration.setAllowedMethods(List.of(
				"GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(List.of("Content-Type"));
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source =
				new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
