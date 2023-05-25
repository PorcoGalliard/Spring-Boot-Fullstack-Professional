package com.amigoscode.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class CustomerJPADataAccessServiceTest {

    private CustomerJPADataAccessService underTest;
    private AutoCloseable autoCloseable;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        underTest = new CustomerJPADataAccessService(customerRepository);
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void selectAllCustomers() {
        //Given

        //When

        //Then
    }

    @Test
    void selectCustomerById() {
        //Given

        //When

        //Then
    }

    @Test
    void insertCustomer() {
        //Given

        //When

        //Then
    }

    @Test
    void deleteCustomerById() {
        //Given

        //When

        //Then
    }

    @Test
    void updateCustomer() {
        //Given

        //When

        //Then
    }

    @Test
    void existPersonWithEmail() {
        //Given

        //When

        //Then
    }

    @Test
    void existPersonWithId() {
        //Given

        //When

        //Then
    }
}