package com.amigoscode.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerDao customerDao;
    private CustomerService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CustomerService(customerDao);
    }

    @Test
    void getAllCustomers() {
        //When
        underTest.getAllCustomers();

        //Then
        Mockito.verify(customerDao)
                .selectAllCustomers();
    }

    @Test
    void getCustomer() {
        //Given
        int id = 10;
        Customer customer = new Customer(
                "Apollo Norm",
                "apollonorm@uncf.com",
                30
        );
        Mockito.when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        //When
        Customer actual = underTest.getCustomer(id);

        //Then
        assertThat(actual).isEqualTo(customer);
    }

    @Test
    void addCustomer() {
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
}