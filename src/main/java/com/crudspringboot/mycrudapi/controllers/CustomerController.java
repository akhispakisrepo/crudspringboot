package com.crudspringboot.mycrudapi.controllers;

import com.crudspringboot.mycrudapi.datamodels.Customer;
import com.crudspringboot.mycrudapi.reposetories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CustomerController {
    @Autowired
    CustomerRepository repository;

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        System.out.println("Get all Customers...");

        List<Customer> customers = new ArrayList<>();
        repository.findAll().forEach(customers::add);

        return customers;
    }

    @PostMapping("/customers/create")
    public Customer postCustomer(@RequestBody Customer customer) {

        Customer _customer = repository.save(new Customer(customer.getName(), customer.getAge()));
        return _customer;
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") String id) {
        System.out.println("Delete Customer with ID = " + id + "...");

        repository.deleteById(id);

        return new ResponseEntity<>("Customer has been deleted!", HttpStatus.OK);
    }

    @DeleteMapping("/customers/delete")
    public ResponseEntity<String> deleteAllCustomers() {
        System.out.println("Delete All Customers...");

        repository.deleteAll();

        return new ResponseEntity<>("All customers have been deleted!", HttpStatus.OK);
    }

    @GetMapping("customers/age/{age}")
    public List<Customer> findByAge(@PathVariable int age) {

        List<Customer> customers = repository.findByAge(age);
        return customers;
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") String id, @RequestBody Customer customer) {
        System.out.println("Update Customer with ID = " + id + "...");

        Optional<Customer> customerData = repository.findById(id);

        if (customerData.isPresent()) {
            Customer _customer = customerData.get();
            _customer.setName(customer.getName());
            _customer.setAge(customer.getAge());
            _customer.setActive(customer.isActive());
            return new ResponseEntity<>(repository.save(_customer), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
