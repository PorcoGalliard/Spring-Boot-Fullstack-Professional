package com.amigoscode.customer;

import com.amigoscode.exception.DuplicateResourceException;
import com.amigoscode.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

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
                id,
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
    void willReturnThrownOptionalWhenIdNotFound() {
        //Given
        int id = 10;

        Mockito.when(customerDao.selectCustomerById(id)).thenReturn(Optional.empty());

        //When
        //Then
        assertThatThrownBy(() -> underTest.getCustomer(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Customer with id = [%s] not found".formatted(id));
    }

    @Test
    void addCustomer() {
        //Given
        String email = "apollonorm@uncf.com";

        Mockito.when(customerDao.existPersonWithEmail(email)).thenReturn(false);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "Apollo Norm", email, 21
        );

        //When
        underTest.addCustomer(request);

        //Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(
                Customer.class
        );

        Mockito.verify(customerDao).insertCustomer(customerArgumentCaptor.capture());

        Customer capturedValue = customerArgumentCaptor.getValue();

        assertThat(capturedValue.getId()).isNull();
        assertThat(capturedValue.getName()).isEqualTo(request.name());
        assertThat(capturedValue.getEmail()).isEqualTo(email);
        assertThat(capturedValue.getAge()).isEqualTo(request.age());

    }

    @Test
    void willThrownWhenEmailAlreadyExistWhileAddingCustomer() {
        //Given
        String email = "apollonorm@uncf.com";

        Mockito.when(customerDao.existPersonWithEmail(email)).thenReturn(true);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "Apollo Norm",
                email,
                21
        );

        //When
        assertThatThrownBy(() -> underTest.addCustomer(request))
                .isInstanceOf(DuplicateResourceException.class)
                        .hasMessage("email already taken");

        //Then
        Mockito.verify(customerDao, Mockito.never()).insertCustomer(any());
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