package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.services.CustomerService;
import com.udacity.jdnd.course3.critter.services.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;
    private final CustomerService customerService;

    public PetController(PetService petService, CustomerService customerService) {
        this.petService = petService;
        this.customerService = customerService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = this.petService.save(this.convertPetDTOToPet(petDTO));
        return convertPetToPetDTO(pet);

    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = this.petService.getPetById(petId);
        PetDTO petDTO = convertPetToPetDTO(pet);

        return petDTO;
    }

    @GetMapping
    public List<PetDTO> getPets(){

        List<Pet> pets = this.petService.getAllPets();

        return this.convertPetsToPetsDTO(pets);
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {

        return this.convertPetsToPetsDTO(this.petService.getPetsByOwner(ownerId));
    }

    private PetDTO convertPetToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);

        return petDTO;
    }

    private Pet convertPetDTOToPet(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);

        if (petDTO.getOwnerId() != 0) {
            Customer owner = this.customerService.getCustomer(petDTO.getOwnerId());
            pet.setCustomer(owner);
        }

        return pet;
    }

    private List<PetDTO> convertPetsToPetsDTO(List<Pet> pets) {
        List<PetDTO> petsDTO = new ArrayList<>();

        for (Pet pet: pets ) {
            petsDTO.add(this.convertPetToPetDTO(pet));
        }

        return petsDTO;
    }
}
