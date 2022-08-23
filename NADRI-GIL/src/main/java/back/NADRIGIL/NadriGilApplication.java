package back.NADRIGIL;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class NadriGilApplication {

	public static void main(String[] args) {
		SpringApplication.run(NadriGilApplication.class, args);
	}

}
