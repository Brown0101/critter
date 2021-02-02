package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.exceptions.NoDataFoundForRequest;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PetService petService;

    public CustomerService(CustomerRepository customerRepository, PetService petService) {
        this.customerRepository = customerRepository;
        this.petService = petService;
    }

    // We will use the repositories build in method findall
    // to return all the customer data in our customer table.
    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }

    // Save our customer object to our database and return the object.
    public Customer save(Customer customer) {
        return this.customerRepository.save(customer);
    }

    public Customer getCustomer(Long id) {
        String message = String.format("No Customer id %s found.", id);

        return customerRepository.findById(id).orElseThrow(() -> new NoDataFoundForRequest(message));
    }

    // Find owner using pet id.
    public Customer getOwnerByPetId(long petId) {
        Long customerId = this.petService.getPetById(petId).getOwnerId();
        String message = String.format("No Customer id %s found for pet id %s.", customerId, petId);
        return this.customerRepository.findById(customerId).orElseThrow(() -> new NoDataFoundForRequest(message));
    }
}
