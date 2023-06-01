package com.amigoscode.customer;

import com.amigoscode.exception.DuplicateResourceException;
import com.amigoscode.exception.RequestValidationException;
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
        int id = 1;

        Mockito.when(customerDao.existPersonWithId(id)).thenReturn(true);

        //When
        underTest.deleteCustomerById(id);

        //Then
        Mockito.verify(customerDao).deleteCustomerById(id);
    }

    @Test
    void cannotDeleteCustomerByIdWhenIdDoesntExist() {
        //Given
        int id = 1;

        Mockito.when(customerDao.existPersonWithId(id)).thenReturn(false);

        //When
        assertThatThrownBy(() -> underTest.deleteCustomerById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(String.format("Customer with id [%d] not found", id));

        //Then
        Mockito.verify(customerDao, Mockito.never()).deleteCustomerById(id);
    }

    @Test
    void canUpdateAllCustomersProperties() {
        //Given
        int id  = 10;
        String email = "antares@uncf.com";

        Customer customer = new Customer(
                id,
                "Apollo Norm",
                "apollonorm@uncf.com",
                30
        );
        Mockito.when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest("Antares", email, 22);

        Mockito.when(customerDao.existPersonWithEmail(email)).thenReturn(false);

        //When
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(
                Customer.class
        );

        underTest.updateCustomer(id, updateRequest);

        //Then
        Mockito.verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(updateRequest.name());
        assertThat(capturedCustomer.getAge()).isEqualTo(updateRequest.age());
        assertThat(capturedCustomer.getEmail()).isEqualTo(updateRequest.email());
    }

    @Test
    void canUpdateOnlyCustomerName() {
        //Given
        int id  = 10;

        Customer customer = new Customer(
                id,
                "Apollo Norm",
                "apollonorm@uncf.com",
                30
        );
        Mockito.when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest("Antares", null, null);

        //When
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(
                Customer.class
        );

        underTest.updateCustomer(id, updateRequest);

        //Then
        Mockito.verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(updateRequest.name());
        assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());
        assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());
    }

    @Test
    void canUpdateOnlyCustomerEmail() {
        //Given
        int id  = 10;

        String email = "antares@uncf.com";

        Customer customer = new Customer(
                id,
                "Apollo Norm",
                "apollonorm@uncf.com",
                30
        );
        Mockito.when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(null, email, null);

        //When
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(
                Customer.class
        );

        underTest.updateCustomer(id, updateRequest);

        //Then
        Mockito.verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());
        assertThat(capturedCustomer.getEmail()).isEqualTo(email);
    }

    @Test
    void canUpdateOnlyCustomerAge() {
        //Given
        int id  = 10;

        Customer customer = new Customer(
                id,
                "Apollo Norm",
                "apollonorm@uncf.com",
                30
        );
        Mockito.when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(null, null, 21);

        //When
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(
                Customer.class
        );

        underTest.updateCustomer(id, updateRequest);

        //Then
        Mockito.verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(capturedCustomer.getAge()).isEqualTo(updateRequest.age());
        assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());
    }

    @Test
    void wilThrownWhenTryingToUpdateCustomerEmailWhenAlreadyTaken() {
        //Given
        int id  = 10;
        String email = "antares@uncf.com";

        Customer customer = new Customer(
                id,
                "Apollo Norm",
                "apollonorm@uncf.com",
                30
        );

        Mockito.when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(null, email, null);

        Mockito.when(customerDao.existPersonWithEmail(email)).thenReturn(true);

        //When
        assertThatThrownBy(() -> underTest.updateCustomer(id, updateRequest))
                .isInstanceOf(DuplicateResourceException.class)
                        .hasMessage("email already taken");

        //Then
        Mockito.verify(customerDao, Mockito.never()).updateCustomer(any());
    }

//    @Test
//    void wilThrownWhenNoChangesProperties() {
//        //Given
//        int id  = 10;
//        Customer customer = new Customer(
//                id, "Apollo Norm", "apollonorm@uncf.com", 30
//        );
//
//        Mockito.when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));
//
//        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
//                customer.getName(), customer.getEmail(), customer.getAge());
//
//        //When
//        assertThatThrownBy(() -> underTest.updateCustomer(id, updateRequest))
//                .isInstanceOf(RequestValidationException.class)
//                        .hasMessage("no data changes found");
//
//        //Then
//        Mockito.verify(customerDao, Mockito.never()).updateCustomer(any());
//    }
}