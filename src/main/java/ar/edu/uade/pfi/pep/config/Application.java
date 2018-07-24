package ar.edu.uade.pfi.pep.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = {"ar.edu.uade.pfi.pep.repository"})
@ComponentScan({"ar.edu.uade.pfi.pep.ejemplos", "ar.edu.uade.pfi.pep.endpoint"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
