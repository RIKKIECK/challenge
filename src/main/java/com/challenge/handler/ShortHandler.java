package com.challenge.handler;

import com.challenge.dto.ShortDto;
import com.challenge.entity.Product;
import com.challenge.service.ShortService;
import com.challenge.validation.ObjectValidator;
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
        Flux<Product> products = shortService.getAll();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(products, Product.class);
    }

    public Mono<ServerResponse> getOne(ServerRequest request) {
        int id = Integer.valueOf(request.pathVariable("id"));
        Mono<Product> product = shortService.getById(id);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(product, Product.class);
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<ShortDto> dtoMono = request.bodyToMono(ShortDto.class).doOnNext(objectValidator::validate);
        return dtoMono.flatMap(shortDto -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(shortService.save(shortDto), Product.class));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        int id = Integer.valueOf(request.pathVariable("id"));
        Mono<ShortDto> dtoMono = request.bodyToMono(ShortDto.class).doOnNext(objectValidator::validate);
        return dtoMono.flatMap(shortDto -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(shortService.update(id, shortDto), Product.class));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        int id = Integer.valueOf(request.pathVariable("id"));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(shortService.delete(id), Product.class);
    }
}
