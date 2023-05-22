package com.amigoscode.customer;

import com.amigoscode.AbstractTestcontainersUnitTest;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CustomerJDBCDataAccessServiceTest extends AbstractTestcontainersUnitTest {

    private CustomerJDBCDataAccessService underTest;
    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();

    @BeforeEach
    void setUp() {
        underTest = new CustomerJDBCDataAccessService(
                getJdbcTemplate(),
                customerRowMapper
        );
    }

    @Test
    void selectAllCustomers() {
        //Given
        Customer customer = new Customer(
                FAKER.name().fullName(),
                FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID(),
                20
        );

        underTest.insertCustomer(customer);

        //When
        List<Customer> actual = underTest.selectAllCustomers();

        //Then
        assertThat(actual).isNotEmpty();
    }

    @Test
    void selectCustomerById() {
        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                20
        );

        underTest.insertCustomer(customer);

        Integer id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        //When
        Optional<Customer> actual = underTest.selectCustomerById(id);

        //Then
        assertThat(actual).isPresent().hasValueSatisfying(c -> {
           assertThat(c.getId().equals(id));
           assertThat(c.getName().equals(customer.getName()));
           assertThat(c.getEmail().equals(customer.getEmail()));
        });
    }

    @Test
    void willReturnEmptyWhenSelectCustomerById() {
        //Given
        int id = -1;

        //When
        Optional<Customer> actual = underTest.selectCustomerById(id);

        //Then
        assertThat(actual).isEmpty();
    }

    @Test
    void insertCustomer() {
    }

    @Test
    void existPersonWithEmail() {
        //Given
        String email = FAKER.internet().safeEmailAddress() + " " + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                21
        );

        underTest.insertCustomer(customer);

        //When
        boolean actual = underTest.existPersonWithEmail(email);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existPersonWithEmailReturnFalseWhenEmailDoesNotExist() {
        //Given
        String email = FAKER.internet().safeEmailAddress() + " " + UUID.randomUUID();

        //When
        boolean actual = underTest.existPersonWithEmail(email);

        //Then
        assertThat(actual).isFalse();

    }

    @Test
    void existPersonWithId() {
        //Given
        String email = FAKER.internet().safeEmailAddress() + " " + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                21
        );

        underTest.insertCustomer(customer);

        Integer id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        //When
        boolean actual = underTest.existPersonWithId(id);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existPersonWithIdReturnFalseWhenIdDoesNotExist() {
        //Given
        int id = -1;

        //When
        boolean actual = underTest.existPersonWithId(id);

        //Then
        assertThat(actual).isFalse();
    }

    @Test
    void deleteCustomerById() {
        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                21
        );

        underTest.insertCustomer(customer);

        Integer id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        //When
        underTest.deleteCustomerById(id);

        //Then
        Optional<Customer> actual = underTest.selectCustomerById(id);
        assertThat(actual).isNotPresent();
    }

    @Test
    void updateCustomer() {
        //Given

        //When

        //Then
    }

    @Test
    void updateCustomerName() {
        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                21
        );

        underTest.insertCustomer(customer);

        Integer id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        var newName = "foo";

        //When
        Customer update = new Customer();
        update.setId(id);
        update.setName(newName);

        underTest.updateCustomer(update);

        //Then
        Optional<Customer> actual = underTest.selectCustomerById(id);
        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId().equals(id));
            assertThat(c.getName().equals(newName));
            assertThat(c.getEmail().equals(customer.getEmail()));
            assertThat(c.getAge().equals(customer.getAge()));
        });
    }

    @Test
    void updateCustomerEmail() {
        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                21
        );

        underTest.insertCustomer(customer);

        Integer id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        var newEmail = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        //When
        Customer update = new Customer();
        update.setId(id);
        update.setEmail(newEmail);

        //Then
        Optional<Customer> actual = underTest.selectCustomerById(id);
        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getEmail().equals(newEmail));
            assertThat(c.getId().equals(id));
            assertThat(c.getName().equals(customer.getName()));
            assertThat(c.getAge().equals(customer.getAge()));
        });
    }

    @Test
    void updateCustomerAge() {
        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                21
        );

        underTest.insertCustomer(customer);

        Integer id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        var newAge = 22;

        //When
        Customer update = new Customer();
        update.setId(id);
        update.setAge(newAge);

        //Then
        Optional<Customer> actual = underTest.selectCustomerById(id);
        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getAge().equals(newAge));
            assertThat(c.getId().equals(id));
            assertThat(c.getName().equals(customer.getName()));
            assertThat(c.getEmail().equals(email));
        });
    }
}