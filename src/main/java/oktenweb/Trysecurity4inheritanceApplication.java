package oktenweb;

import oktenweb.models.Client;
import oktenweb.models.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Trysecurity4inheritanceApplication {

	Client client = new Client();
	User user = new User();
	public static void main(String[] args) {


		SpringApplication.run(Trysecurity4inheritanceApplication.class, args);
	}

}
