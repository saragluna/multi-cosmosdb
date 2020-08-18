package com.azure.datasource.secondary.database4;

import com.azure.spring.data.cosmos.core.mapping.Container;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Container(containerName = "delivery-order", ru = "400")
public class DeliveryOrder {
    @Id
    private String id;

    private String orderId;

    private String status;

}
