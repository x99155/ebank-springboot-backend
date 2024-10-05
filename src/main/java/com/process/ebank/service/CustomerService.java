package com.process.ebank.service;

import com.process.ebank.dto.CustomerDto;
import com.process.ebank.entity.Customer;

import java.util.List;

public interface CustomerService {

    CustomerDto saveCustomer(CustomerDto customerDto);

    List<CustomerDto> listCustomers();

    CustomerDto getCustomer(Long customerId);

    CustomerDto updateCustomer(CustomerDto customerDto);

    void deleteCustomer(Long id);

    List<CustomerDto> searchCustomers(String keyword);
}
