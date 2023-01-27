package be.technifutur.java.timairport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TimairportApplication {

	public static void main(String[] args) {
//		SpringApplication.run(TimairportApplication.class, args);


		RuntimeException exCause = new RuntimeException("message de cause");

		RuntimeException exEnglobante = new RuntimeException("message de l'exception englobante", exCause);

		throw exEnglobante;

	}

}
