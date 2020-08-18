package com.azure.datasource.secondary.database3;


import com.azure.spring.data.cosmos.repository.CosmosRepository;

public interface OrderRepository extends CosmosRepository<Order, String> {
}
