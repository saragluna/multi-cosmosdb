package com.azure.datasource.secondary.database3;

import com.azure.spring.data.cosmos.core.mapping.Container;
import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Container(containerName = "orders", ru = "400")
public class Order {
    @Id
    private String id;

    private String userId;

    private List<String> books;

}
