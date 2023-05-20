package com.amigoscode;

import com.amigoscode.customer.Customer;
import com.amigoscode.customer.CustomerRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Random;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository) {
        return args -> {
            var faker = new Faker();
            var random = new Random();
            String firstName = faker.name().firstName().toLowerCase();
            String lastName = faker.name().lastName().toLowerCase();
            Customer customer = new Customer(
                    firstName + " " + lastName,
                    firstName + "." + lastName + "@amigoscode.com",
                    random.nextInt(16, 99)
            );

            customerRepository.save(customer);
        };
    }
}

