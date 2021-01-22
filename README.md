# Demo for how to connect to multiple Cosmos datasources using spring-data-cosmos

spring-data-cosmos is a Spring Data integration for Azure CosmosDB. Check [here](https://github.com/Azure/azure-sdk-for-java/tree/master/sdk/cosmos/azure-spring-data-cosmos) for more details. 

## How to run

- Check out this project.
- Fill out the connection info of two Cosmos DBs in the application.properties. 
```yaml
azure:
  cosmos:
    primary:
      uri: $(primary-database-uri)
      key: $(primary-database-key)
    secondary:
      uri: $(secondary-database-uri)
      key: $(secondary-database-key)
```
- Run the application in your IDE or via `mvn spring-boot:run`.
