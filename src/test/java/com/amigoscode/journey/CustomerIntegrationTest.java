package com.amigoscode.journey;

import com.amigoscode.customer.CustomerRegistrationRequest;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Random;
import java.util.UUID;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CustomerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    private static final Random RANDOM = new Random();

    @Test
    void canRegisterACustomer() {
        //Given

        // Create Registration request
        Faker faker = new Faker();
        Name fakerName = faker.name();
        String name = fakerName.fullName();
        String email = fakerName.lastName() + UUID.randomUUID() + "@amigoscode.com";
        int age = RANDOM.nextInt(1, 100);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
            name, email, age
        );

        // Send a post request
        webTestClient.post()
                .uri("/api/v1/customers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();
        //When

        //Then
    }
}
