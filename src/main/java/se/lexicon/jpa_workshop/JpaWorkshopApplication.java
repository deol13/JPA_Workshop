package se.lexicon.jpa_workshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import se.lexicon.jpa_workshop.repository.AppUserRepository;

@SpringBootApplication
public class JpaWorkshopApplication {

	public static void main(String[] args) {

		SpringApplication.run(JpaWorkshopApplication.class, args);
	}

}
