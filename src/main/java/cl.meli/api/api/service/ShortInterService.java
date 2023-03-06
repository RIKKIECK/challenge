package cl.meli.api.api.service;


import cl.meli.api.api.redis.ShortUrl;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ShortInterService {

    String getOriginalUrlByKey(@NotBlank String key);

    ShortUrl shortenUrl(@NotBlank String originalUrl);

}
