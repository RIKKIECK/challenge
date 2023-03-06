package cl.meli.api.api.repository;

import cl.meli.api.api.entity.ShortEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ShortRepository extends ReactiveCrudRepository<ShortEntity, Integer>{
    Mono<ShortEntity> findByName(String name);

    @Query("SELECT * FROM product WHERE id <> :id AND name = :name")
    Mono<ShortEntity> repeatedName(int id, String name);
}
