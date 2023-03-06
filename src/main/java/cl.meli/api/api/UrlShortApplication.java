package cl.meli.api.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@RequiredArgsConstructor
@SpringBootApplication
@OpenAPIDefinition(info = @Info(
		title = "Challenge",
		version = "1.0",
		description = "sample documents"
))
public class UrlShortApplication {

	Environment environment;

	public static void main(String[] args) {
		SpringApplication.run(UrlShortApplication.class, args);
	}


	@Bean
	public LettuceConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(
				environment.getProperty("redis.host", "localhost"),
				environment.getProperty("redis.port", Integer.class, 6379));
		configuration.setUsername(environment.getProperty("redis.username", "default"));
		configuration.setPassword(RedisPassword.of(environment.getProperty("redis.password", "")));
		return new LettuceConnectionFactory(configuration);
	}

}
