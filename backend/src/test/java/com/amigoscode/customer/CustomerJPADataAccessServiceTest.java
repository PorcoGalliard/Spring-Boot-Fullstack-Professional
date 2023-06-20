package com.amigoscode.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class CustomerJPADataAccessServiceTest {

    private CustomerJPADataAccessService underTest;
    private AutoCloseable autoCloseable;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerJPADataAccessService(customerRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void selectAllCustomers() {
        //When
        underTest.selectAllCustomers();

        //Then
        Mockito.verify(customerRepository)
                .findAll();
    }

    @Test
    void selectCustomerById() {
        //Given
        int id = 1;

        //When
        underTest.selectCustomerById(id);

        //Then
        Mockito.verify(customerRepository)
                .findById(id);
    }

    @Test
    void insertCustomer() {
        //Given
        Customer customer = new Customer(
                "Ali",
                "aliexpress@amigoscode.com",
                "password", 21,
                Gender.MALE);

        //When
        underTest.insertCustomer(customer);

        //Then
        Mockito.verify(customerRepository)
                .save(customer);
    }

    @Test
    void deleteCustomerById() {
        //Given
        int id = 1;

        //When
        underTest.deleteCustomerById(id);

        //Then
        Mockito.verify(customerRepository)
                .deleteById(id);
    }

    @Test
    void updateCustomer() {
        //Given
        Customer customer = new Customer(
                "Yamato",
                "yamatomusashi@japan.com",
                "password", 41,
                Gender.MALE);

        //When
        underTest.updateCustomer(customer);

        //Then
        Mockito.verify(customerRepository)
                .save(customer);
    }

    @Test
    void existPersonWithEmail() {
        //Given
        String email = "amigoscodelearning@amigoscode.com";

        //When
        underTest.existPersonWithEmail(email);

        //Then
        Mockito.verify(customerRepository)
                .existsCustomerByEmail(email);
    }

    @Test
    void existPersonWithId() {
        //Given
        int id = 1;

        //When
        underTest.existPersonWithId(id);

        //Then
        Mockito.verify(customerRepository)
                .existsCustomerById(id);
    }
}