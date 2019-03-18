package com.crudspringboot.mycrudapi.reposetories;

import com.crudspringboot.mycrudapi.datamodels.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CustomerRepository extends MongoRepository<Customer, String> {
    List<Customer> findByAge(int age);
}
