package com.udacity.jdnd.course3.critter.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", length = 100)
    private String name;
    @Column(name = "phone_number", length = 15)
    private String phoneNumber;
    @Column(name = "notes", length = 500)
    private String notes;

    @OneToMany(targetEntity = Pet.class, cascade = CascadeType.ALL)
    private List<Pet> petIds;

    public Customer(String name, String phoneNumber, String notes, List<Pet> petIds) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.notes = notes;
        this.petIds = petIds;
    }

    public Customer() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void addPet(Pet pet) {
        this.petIds.add(pet);
    }

    public List<Pet> getPetIds() {
        return petIds;
    }

    public void setPetIds(List<Pet> petIds) {

        if(petIds == null) {
            petIds = new ArrayList<>();
        }
        this.petIds = petIds;
    }
}
