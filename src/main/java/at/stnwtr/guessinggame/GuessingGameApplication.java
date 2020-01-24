package at.stnwtr.guessinggame;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GuessingGameApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(GuessingGameApplication.class, args);
	}

	@Override
	public void run(String... args) {
		// Entry Point
	}
}
