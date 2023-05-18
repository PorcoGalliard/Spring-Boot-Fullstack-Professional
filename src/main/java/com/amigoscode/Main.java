package com.amigoscode;

import com.amigoscode.customer.Customer;
import com.amigoscode.customer.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository) {
        return args -> {
            Customer jamila = new Customer(
                    "Jamila",
                    "jamila@gmail.com",
                    19);

            Customer alex = new Customer(
                    "Alex",
                    "alex@gmail.com",
                    18
            );

            List<Customer> customers = List.of(jamila, alex);
            customerRepository.saveAll(customers);
        };
    }
}

