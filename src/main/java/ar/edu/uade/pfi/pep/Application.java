package ar.edu.uade.pfi.pep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = { "ar.edu.uade.pfi.pep.repository" })
@ComponentScan({ "ar.edu.uade.pfi.pep.config", "ar.edu.uade.pfi.pep.controller", "ar.edu.uade.pfi.pep.service",
		"ar.edu.uade.pfi.pep.common", })
public class Application {

	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		Application.LOGGER.debug("--Application Started--");
	}
}
