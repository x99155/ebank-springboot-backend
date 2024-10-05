package com.process.ebank.repository;

import com.process.ebank.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByNameContains(String keyword);

    // OU avec une requete HQL
    //@Query("select c from Customer c where c.name like :kw")
    //List<Customer> searchCustomer(@Param("kw") String keyword);
}
