package com.amigoscode.customer;

import com.amigoscode.exception.DuplicateResourceException;
import com.amigoscode.exception.RequestValidationException;
import com.amigoscode.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(@Qualifier("jpa") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers() {
        return customerDao.selectAllCustomers();
    }

    public Customer getCustomer(Integer id) {
        return customerDao.selectCustomerById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with id = [%s] not found".formatted(id)));
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        String email = customerRegistrationRequest.email();
        if (customerDao.existPersonWithEmail(email)) {
            throw new DuplicateResourceException("email already taken".formatted(email));
        }
        Customer customer = new Customer(customerRegistrationRequest.name(), customerRegistrationRequest.email(), customerRegistrationRequest.age()
        );
        customerDao.insertCustomer(customer);
    }

    public void deleteCustomerById(Integer id) {
        if (!customerDao.existPersonWithId(id)) {
            throw new ResourceNotFoundException(
                    "Customer with id [%d] not found".formatted(id)
            );
        }

        customerDao.deleteCustomerById(id);
    }

    public void updateCustomer(Integer id, CustomerUpdateRequest updateRequest) {
        Customer customer = getCustomer(id);
        boolean changes = false;

        if (updateRequest.name() != null && !updateRequest.name().equals(customer.getName())) {
            customer.setName(updateRequest.name());
            changes = true;
        }

        if (updateRequest.email() != null && !updateRequest.email().equals(customer.getEmail())) {
            if (customerDao.existPersonWithEmail(updateRequest.email())) {
                throw new DuplicateResourceException("email already taken");
            }
            customer.setEmail(updateRequest.email());
            changes = true;
        }

        if (updateRequest.age() != null && !updateRequest.age().equals(customer.getName())) {
            customer.setAge(updateRequest.age());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("no data changes found");
        }

        customerDao.updateCustomer(customer);
    }
}
