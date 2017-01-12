package matt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:META-INF/spring/spring-integration-context.xml")
public class SpringIntegrationTestDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringIntegrationTestDemoApplication.class, args);
	}
}
