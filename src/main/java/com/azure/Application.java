package com.azure;

import com.azure.cosmos.implementation.guava25.collect.Lists;
import com.azure.datasource.primary.database1.Book;
import com.azure.datasource.primary.database1.BookRepository;
import com.azure.datasource.primary.database2.User;
import com.azure.datasource.primary.database2.UserRepository;
import com.azure.datasource.secondary.database3.Order;
import com.azure.datasource.secondary.database3.OrderRepository;
import com.azure.datasource.secondary.database4.DeliveryOrder;
import com.azure.datasource.secondary.database4.DeliveryOrderRepository;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {

    private static final String ORDER_ID = "order_1";
    private static final String DELIVERY_ORDER_ID = "delivery_order_1";

    private final User user = new User("1024", "Jack", "1024@geek.com", "Mars");
    private final Book book = new Book("9780792745", "Zen and the Art of Motorcycle Maintenance", "Robert M. Pirsig");

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DeliveryOrderRepository deliveryRepository;


    @PostConstruct
    public void setup() {
        this.userRepository.save(user).block();
        this.bookRepository.save(book).block();
    }

    @PreDestroy
    public void cleanup() {
        this.userRepository.deleteAll().block();
        this.bookRepository.deleteAll().block();
        this.orderRepository.deleteAll();
        this.deliveryRepository.deleteAll();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        Order newOrder = new Order();
        newOrder.setId(ORDER_ID);
        newOrder.setUserId(user.getId());
        newOrder.setBooks(Lists.newArrayList(book.getIbsn()));
        final Order savedOrder = orderRepository.save(newOrder);

        log.info("New order has been placed: {}", savedOrder);

        DeliveryOrder deliveryOrder = new DeliveryOrder();
        deliveryOrder.setId(DELIVERY_ORDER_ID);
        deliveryOrder.setOrderId(savedOrder.getId());
        deliveryOrder.setStatus("Delivery");
        final DeliveryOrder savedDeliveryOrder = deliveryRepository.save(deliveryOrder);

        log.info("New delivery order: {}", savedDeliveryOrder);
    }


}
