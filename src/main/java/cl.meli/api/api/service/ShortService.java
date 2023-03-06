package cl.meli.api.api.service;
import cl.meli.api.api.dto.ShortDto;
import cl.meli.api.api.entity.ShortEntity;
import cl.meli.api.api.exception.CustomException;
import cl.meli.api.api.repository.ShortRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShortService {
    private final static String NF_MESSAGE = "product not found";
    private final static String NAME_MESSAGE = "product name already in use";

    private final ShortRepository shortRepository;

    public Flux<ShortEntity> getAll() {
        return shortRepository.findAll();
    }

    public Mono<ShortEntity> getById(int id) {
        return shortRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, NF_MESSAGE)));
    }

    public Mono<ShortEntity> save(ShortDto dto) {
        Mono<Boolean> existsName = shortRepository.findByName(dto.getName()).hasElement();
        return existsName.flatMap(exists -> exists ? Mono.error(new CustomException(HttpStatus.BAD_REQUEST, NAME_MESSAGE))
                : shortRepository.save(ShortEntity.builder().name(dto.getName()).price(dto.getPrice()).build()));
    }

    public Mono<ShortEntity> update(int id, ShortDto dto) {
        Mono<Boolean> productId = shortRepository.findById(id).hasElement();
        Mono<Boolean> productRepeatedName = shortRepository.repeatedName(id, dto.getName()).hasElement();
        return productId.flatMap(
                existsId -> existsId ?
                        productRepeatedName.flatMap(existsName -> existsName ? Mono.error(new CustomException(HttpStatus.BAD_REQUEST, NAME_MESSAGE))
                                : shortRepository.save(new ShortEntity(id, dto.getName(), dto.getPrice())))
                        : Mono.error(new CustomException(HttpStatus.NOT_FOUND, NF_MESSAGE)));
    }

    public Mono<Void> delete(int id) {
        Mono<Boolean> productId = shortRepository.findById(id).hasElement();
        return productId.flatMap(exists -> exists ? shortRepository.deleteById(id) : Mono.error(new CustomException(HttpStatus.NOT_FOUND, NF_MESSAGE)));
    }
}
