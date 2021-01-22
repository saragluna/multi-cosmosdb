package com.azure.configuration;

import com.azure.cosmos.CosmosAsyncClient;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.spring.data.cosmos.config.AbstractCosmosConfiguration;
import com.azure.spring.data.cosmos.config.CosmosConfig;
import com.azure.spring.data.cosmos.core.ReactiveCosmosTemplate;
import com.azure.spring.data.cosmos.core.convert.MappingCosmosConverter;
import com.azure.spring.data.cosmos.repository.config.EnableReactiveCosmosRepositories;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This configures the first Cosmos DB account.
 */
@Slf4j
@Configuration
public class PrimaryCosmosConfiguration {

    private static final String DATABASE1 = "primary_database1";
    private static final String DATABASE2 = "primary_database2";

    @Bean
    @ConfigurationProperties(prefix = "azure.cosmos.primary")
    public CosmosDbProperties primary() {
        return new CosmosDbProperties();
    }

    @Bean
    public CosmosClientBuilder primaryClientBuilder(@Qualifier("primary") CosmosDbProperties primaryProperties) {
        return new CosmosClientBuilder()
            .key(primaryProperties.getKey())
            .endpoint(primaryProperties.getUri());
    }

    @EnableReactiveCosmosRepositories(basePackages = "com.azure.datasource.primary.database1")
    public static class DataBase1Configuration extends AbstractCosmosConfiguration {

        @Override
        protected String getDatabaseName() {
            return DATABASE1;
        }

    }

    @EnableReactiveCosmosRepositories(basePackages = "com.azure.datasource.primary.database2",
                                      reactiveCosmosTemplateRef = "primaryDatabase2Template")
    public static class Database2Configuration {

        @Bean
        public ReactiveCosmosTemplate primaryDatabase2Template(CosmosAsyncClient cosmosAsyncClient,
            CosmosConfig cosmosConfig,
            MappingCosmosConverter mappingCosmosConverter) {

            return new ReactiveCosmosTemplate(cosmosAsyncClient, DATABASE2, cosmosConfig, mappingCosmosConverter);
        }
    }

}

