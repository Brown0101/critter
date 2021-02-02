package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.pet.PetType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Optional;

@Entity
public class Pet {

    @Id
    @GeneratedValue
    private Long id;

    private PetType type;
    private String name;
    private Long ownerId;
    private LocalDate birthDate;
    private String notes;

    @ManyToOne(cascade = CascadeType.ALL)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "pet_schedule", nullable = true)
    private Schedule schedule;

    public Pet(PetType type, String name, Long ownerId, LocalDate birthDate,
               String notes, Customer customer, Schedule schedule) {
        this.type = type;
        this.name = name;
        this.ownerId = ownerId;
        this.birthDate = birthDate;
        this.notes = notes;
        this.customer = customer;
        this.schedule = schedule;
    }

    public Pet() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
