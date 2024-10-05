package com.process.ebank.web;

import com.process.ebank.dto.CustomerDto;
import com.process.ebank.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerRestController {

    private final CustomerService customerService;

    @GetMapping("/customers")
    public List<CustomerDto> getAll() {
        return customerService.listCustomers();
    }

    @GetMapping("/customers/{id}")
    public CustomerDto get(@PathVariable Long id) {
        return customerService.getCustomer(id);
    }

    @PostMapping("/customers")
    public CustomerDto create(@RequestBody CustomerDto request) {
        return customerService.saveCustomer(request);
    }

    @PutMapping("/customers/{id}")
    public CustomerDto update(@PathVariable Long id, @RequestBody CustomerDto request) {
        request.setId(id);
        return customerService.updateCustomer(request);
    }

    @DeleteMapping("/customers/{id}")
    public void delete(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }

    @GetMapping("/customers/search") //ex: localhost:9000/customers/search?keyword=boris
    public List<CustomerDto> search(@RequestParam(name = "keyword", defaultValue = "") String keyword) {
        return customerService.searchCustomers(keyword);
    }
}
