package com.amigoscode.customer;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CustomerRowMapperTest {

    @Test
    void mapRow() throws SQLException {
        //Given
        CustomerRowMapper customerRowMapper = new CustomerRowMapper();

        ResultSet resultSet = mock(ResultSet.class);
        Mockito.when(resultSet.getInt("id")).thenReturn(1);
        Mockito.when(resultSet.getInt("age")).thenReturn(19);
        Mockito.when(resultSet.getString("name")).thenReturn("Apollo Norm");
        Mockito.when(resultSet.getString("email")).thenReturn("apollonorm@uncf.com");

        //When
        Customer actual = (Customer) customerRowMapper.mapRow(resultSet, 1);

        //Then
        Customer expected = new Customer(
                1, "Apollo Norm", "apollonorm@uncf.com", 19
        );

        assertThat(actual).isEqualTo(expected);
    }
}