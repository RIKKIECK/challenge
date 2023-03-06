package cl.meli.api.api.handler;

import cl.meli.api.api.dto.ShortDto;
import cl.meli.api.api.entity.ShortEntity;
import cl.meli.api.api.service.ShortService;
import cl.meli.api.api.validation.ObjectValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class ShortHandler {

    private final ShortService shortService;

    private final ObjectValidator objectValidator;

    public Mono<ServerResponse> getAll(ServerRequest request) {
        Flux<ShortEntity> products = shortService.getAll();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(products, ShortEntity.class);
    }

    public Mono<ServerResponse> getOne(ServerRequest request) {
        int id = Integer.valueOf(request.pathVariable("id"));
        Mono<ShortEntity> product = shortService.getById(id);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(product, ShortEntity.class);
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<ShortDto> dtoMono = request.bodyToMono(ShortDto.class).doOnNext(objectValidator::validate);
        return dtoMono.flatMap(shortDto -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(shortService.save(shortDto), ShortEntity.class));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        int id = Integer.valueOf(request.pathVariable("id"));
        Mono<ShortDto> dtoMono = request.bodyToMono(ShortDto.class).doOnNext(objectValidator::validate);
        return dtoMono.flatMap(shortDto -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(shortService.update(id, shortDto), ShortEntity.class));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        int id = Integer.valueOf(request.pathVariable("id"));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(shortService.delete(id), ShortEntity.class);
    }
}
