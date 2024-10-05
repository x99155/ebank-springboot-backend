package com.process.ebank.mapper;

import com.process.ebank.dto.CustomerDto;
import com.process.ebank.entity.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerDto toCustomer(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(customer, customerDto);
        //customerDto.setName(customerDto.getName());
        //customerDto.setEmail(customerDto.getEmail());
        return customerDto;
    }

    public Customer toCustomerDto(CustomerDto customerDto) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDto, customer);
        return customer;
    }
}
