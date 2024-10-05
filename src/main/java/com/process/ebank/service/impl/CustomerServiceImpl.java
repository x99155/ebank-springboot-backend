package com.process.ebank.service.impl;

import com.process.ebank.dto.CustomerDto;
import com.process.ebank.entity.Customer;
import com.process.ebank.exception.CustomerNotFoundException;
import com.process.ebank.mapper.CustomerMapper;
import com.process.ebank.repository.CustomerRepository;
import com.process.ebank.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto) {
        // transfert le dto vers l'entité
        Customer customer = customerMapper.toCustomerDto(customerDto);

        // j'enregistre en bdd
        Customer savedCustomer = customerRepository.save(customer);

        return customerMapper.toCustomer(savedCustomer);
    }

    @Override
    public List<CustomerDto> listCustomers() {
        List<Customer> customers =  customerRepository.findAll();
        List<CustomerDto> customerDtos = customers
                .stream()
                .map(customer -> customerMapper.toCustomer(customer))
                .collect(Collectors.toList());

        return customerDtos;
    }

    @Override
    public CustomerDto getCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new CustomerNotFoundException("Not found!")
        );

        return customerMapper.toCustomer(customer);
    }

    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto) {
        // transfert le dto vers l'entité
        Customer customer = customerMapper.toCustomerDto(customerDto);

        // j'enregistre en bdd
        Customer savedCustomer = customerRepository.save(customer);

        return customerMapper.toCustomer(savedCustomer);
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public List<CustomerDto> searchCustomers(String keyword) {
        List<Customer> customers = customerRepository.findByNameContains(keyword);

        return customers
                .stream()
                .map(customerMapper::toCustomer)
                .collect(Collectors.toList());
    }
}
