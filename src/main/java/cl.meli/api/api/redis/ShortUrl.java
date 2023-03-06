package cl.meli.api.api.redis;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrl {

    @NotBlank
    private String key;

    @NotBlank
    private String originalUrl;

}
