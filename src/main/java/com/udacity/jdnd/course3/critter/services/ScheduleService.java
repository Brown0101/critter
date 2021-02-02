package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.exceptions.NoDataFoundForRequest;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final EmployeeRepository employeeRepository;
    private final PetRepository petRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, EmployeeRepository employeeRepository, PetRepository petRepository) {
        this.scheduleRepository = scheduleRepository;
        this.employeeRepository = employeeRepository;
        this.petRepository = petRepository;
    }

    // We can create new schedules for pets with employees.
    public Schedule createSchedule(Schedule schedule, List<Long> petIds, List<Long> employeeIds) {
        String petMessage = String.format("No pet exists for pet id provided.");
        String employeeMessage = String.format("No employee exists for the data provided.");

        List<Pet> pets = new ArrayList<>();
        List<Employee> employees = new ArrayList<>();

        for(Long petId : petIds) {
            pets.add(petRepository.findById(petId).orElseThrow(
                    () -> new NoDataFoundForRequest(petMessage)
            ));
        }

        for(Long employeeId : employeeIds) {
            employees.add(employeeRepository.findById(employeeId).orElseThrow(
                    () -> new NoDataFoundForRequest(employeeMessage)
            ));
        }

        schedule.setPets(pets);
        schedule.setEmployees(employees);
        Schedule newSchedule = scheduleRepository.save(schedule);

        for(Pet pet : pets) {
            this.petRepository.findById(pet.getId()).orElseThrow(
                    () -> new NoDataFoundForRequest(petMessage)
            ).setSchedule(schedule);
        }

        for(Employee employee : employees) {
            this.employeeRepository.findById(employee.getId()).orElseThrow(
                    () -> new NoDataFoundForRequest(employeeMessage)
            ).setSchedule(schedule);
        }

        return newSchedule;
    }

    public List<Schedule> getAllSchedules() {

        return this.scheduleRepository.findAll();
    }

    // We can et individual pet schedules.
    public List<Schedule> getPetSchedules(Long petId) {
        List<Schedule> schedules = scheduleRepository.findAll();
        List<Schedule> petSchedules = new ArrayList<>();

        for(Schedule schedule : schedules) {
            for(Pet pet : schedule.getPets()) {
                if (pet.getId().equals(petId)) {
                    petSchedules.add(schedule);
                }
            }
        }

        return petSchedules;
    }

    // We can get a list of schedules per employee.
    public List<Schedule> getEmployeeSchedules(Long employeeId) {
        List<Schedule> schedules = scheduleRepository.findAll();
        List<Schedule> employeeSchedules = new ArrayList<>();

        for(Schedule schedule : schedules) {
            for(Employee employee : schedule.getEmployees()) {
                if (employee.getId().equals(employeeId)) {
                    employeeSchedules.add(schedule);
                }
            }
        }

        return employeeSchedules;
    }

    // We can get a list of schedules per customer with pets.
    public List<Schedule> getCustomerSchedules(Long customerId) {
        List<Schedule> schedules = scheduleRepository.findAll();
        List<Schedule> customerSchedules = new ArrayList<>();

        for(Schedule schedule : schedules) {
            for(Pet pet : schedule.getPets()) {
                if (pet.getCustomer().getId() == customerId) {
                    customerSchedules.add(schedule);
                }
            }
        }

        return customerSchedules;
    }
}
