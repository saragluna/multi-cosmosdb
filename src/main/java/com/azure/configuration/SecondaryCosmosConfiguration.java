package com.azure.configuration;

import com.azure.cosmos.CosmosAsyncClient;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.spring.data.cosmos.CosmosFactory;
import com.azure.spring.data.cosmos.config.CosmosConfig;
import com.azure.spring.data.cosmos.core.CosmosTemplate;
import com.azure.spring.data.cosmos.core.ResponseDiagnostics;
import com.azure.spring.data.cosmos.core.ResponseDiagnosticsProcessor;
import com.azure.spring.data.cosmos.core.convert.MappingCosmosConverter;
import com.azure.spring.data.cosmos.repository.config.EnableCosmosRepositories;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

/**
 * This configures the second Cosmos DB account.
 */
@Slf4j
@Configuration
public class SecondaryCosmosConfiguration {

    public static final String DATABASE3 = "secondary_database3";
    public static final String DATABASE4 = "secondary_database4";

    @Bean
    @ConfigurationProperties(prefix = "azure.cosmosdb.secondary")
    public CosmosDbProperties secondary() {
        return new CosmosDbProperties();
    }

    @Bean("secondaryCosmosClient")
    public CosmosAsyncClient getCosmosAsyncClient(@Qualifier("secondary") CosmosDbProperties secondaryProperties) {
        return CosmosFactory.createCosmosAsyncClient(new CosmosClientBuilder()
                .key(secondaryProperties.getKey())
                .endpoint(secondaryProperties.getUri()));
    }

    @Bean("secondaryCosmosConfig")
    public CosmosConfig getCosmosConfig() {
        return CosmosConfig.builder()
            .enableQueryMetrics(true)
            .responseDiagnosticsProcessor(new ResponseDiagnosticsProcessorImplementation())
            .build();
    }

    @EnableCosmosRepositories(basePackages = "com.azure.datasource.secondary.database3",
                              cosmosTemplateRef = "secondaryDatabase3Template")
    public static class Database3Configuration {

        @Bean
        public CosmosTemplate secondaryDatabase3Template(@Qualifier("secondaryCosmosClient") CosmosAsyncClient client,
                                                         @Qualifier("secondaryCosmosConfig") CosmosConfig cosmosConfig,
                                                         MappingCosmosConverter mappingCosmosConverter) {
            return new CosmosTemplate(client, DATABASE3, cosmosConfig, mappingCosmosConverter);
        }
    }

    @EnableCosmosRepositories(basePackages = "com.azure.datasource.secondary.database4",
                              cosmosTemplateRef = "secondaryDatabase4Template")
    public static class Database4Configuration {

        @Bean
        public CosmosTemplate secondaryDatabase4Template(@Qualifier("secondaryCosmosClient") CosmosAsyncClient client,
                                                         @Qualifier("secondaryCosmosConfig") CosmosConfig cosmosConfig,
                                                         MappingCosmosConverter mappingCosmosConverter) {

            return new CosmosTemplate(client, DATABASE4, cosmosConfig, mappingCosmosConverter);
        }
    }

    private static class ResponseDiagnosticsProcessorImplementation implements ResponseDiagnosticsProcessor {

        @Override
        public void processResponseDiagnostics(@Nullable ResponseDiagnostics responseDiagnostics) {
            log.info("Response Diagnostics {}", responseDiagnostics);
        }
    }

}

