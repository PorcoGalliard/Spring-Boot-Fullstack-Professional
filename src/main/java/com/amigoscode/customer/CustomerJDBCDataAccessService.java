package com.amigoscode.customer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;

    public CustomerJDBCDataAccessService(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    @Override
    public List<Customer> selectAllCustomers() {
        var sql = """
                SELECT id, name, email, age
                FROM customer;
                """;

        List<Customer> customers = jdbcTemplate.query(sql, customerRowMapper);
        return customers;

    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        return Optional.empty();
    }

    @Override
    public void insertCustomer(Customer customer) {
        var sql = """
                INSERT INTO customer (name, email, age)
                VALUES (?, ?, ?)
                """;

        int result = jdbcTemplate.update(
                sql,
                customer.getName(),
                customer.getEmail(),
                customer.getAge()
        );

        System.out.println("JDBC Template: " + result);
    }

    @Override
    public boolean existPersonWithEmail(String email) {
        return false;
    }

    @Override
    public boolean existPersonWithId(Integer id) {
        return false;
    }

    @Override
    public void deleteCustomerById(Integer id) {

    }

    @Override
    public void updateCustomer(Customer update) {

    }
}
