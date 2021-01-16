package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.udacity.jdnd.course3.critter.exceptions. NoDataFoundForRequest;

@Service
public class PetService {
    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    public PetService(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    // Allows us to save our new pet added by controller
    public Pet save(Pet pet) {
        Pet returnedPet = petRepository.save(pet);
        Customer customer = returnedPet.getCustomer();
        customer.addPet(returnedPet);

        return returnedPet;
    }

    // We are abel to find our pets by an ID provided.
    public Pet getPetById(Long id) {
        String message = String.format("No pet exists or the id %s provided.", id);

        return this.petRepository.findById(id).orElseThrow(() -> new NoDataFoundForRequest(message));
    }

    // List all pets
    public List<Pet> getAllPets() {
        return this.petRepository.findAll();
    }

    // Provides a list of pets owned by an owner id provided
    public List<Pet> getPetsByOwner(long ownerId) {
        String message = String.format("Customer does NOT exist for %s provided.", ownerId);
        Customer customer = this.customerRepository.findById(ownerId).orElseThrow(() -> new NoDataFoundForRequest(message));

        return this.petRepository.findByCustomer(Optional.ofNullable(customer));
    }
}
