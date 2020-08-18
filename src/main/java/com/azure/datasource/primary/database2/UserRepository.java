package com.azure.datasource.primary.database2;

import com.azure.spring.data.cosmos.repository.ReactiveCosmosRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserRepository extends ReactiveCosmosRepository<User, String> {

    Flux<User> findByName(String firstName);

    Flux<User> findByEmailOrName(String email, String name);

}