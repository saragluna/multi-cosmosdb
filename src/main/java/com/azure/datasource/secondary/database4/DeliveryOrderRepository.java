package com.azure.datasource.secondary.database4;


import com.azure.spring.data.cosmos.repository.CosmosRepository;

public interface DeliveryOrderRepository extends CosmosRepository<DeliveryOrder, String> {
}
