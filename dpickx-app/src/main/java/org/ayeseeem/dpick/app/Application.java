package org.ayeseeem.dpick.app;

import static java.util.Arrays.asList;

import org.ayeseeem.dpick.util.xml.DomDump;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * Spring Boot convenience wrapper to simplify making a runnable Jar file.
 * @see App
 */
@SpringBootApplication
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(DomDump.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            asList(args).forEach(logger::debug);

            App.effectiveMain(args);

            SpringApplication.exit(ctx, () -> 0);
        };
    }
}
