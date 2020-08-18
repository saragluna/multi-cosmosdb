package com.azure.datasource.primary.database1;


import com.azure.spring.data.cosmos.repository.ReactiveCosmosRepository;

public interface BookRepository extends ReactiveCosmosRepository<Book, String> {
}
