package modi1.dummy11;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Dummy11Application{
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args)  {

		SpringApplication.run(Dummy11Application.class, args);




	}



}
