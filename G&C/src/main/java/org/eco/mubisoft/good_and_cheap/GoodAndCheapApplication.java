package org.eco.mubisoft.good_and_cheap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * <p><b>Good and Cheap Application</b></p>
 * <p>The main launch class for the application.</p>
 */
@SpringBootApplication
public class GoodAndCheapApplication {

    /**
     * Main starting function.
     * @param args Arguments passed before execution.
     */
    public static void main(String[] args) {
        SpringApplication.run(GoodAndCheapApplication.class, args);
    }

    /**
     * Get a password encoder.
     * @return A BCryptPasswordEncoder.
     */
    @Bean
    BCryptPasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
}
