package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.services.EmployeeService;
import com.udacity.jdnd.course3.critter.services.PetService;
import com.udacity.jdnd.course3.critter.services.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final EmployeeService employeeService;
    private final PetService petService;

    public ScheduleController(ScheduleService scheduleService, EmployeeService employeeService, PetService petService) {
        this.scheduleService = scheduleService;
        this.employeeService = employeeService;
        this.petService = petService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();

        BeanUtils.copyProperties(scheduleDTO, schedule);

        ScheduleDTO newScheduleDTO = this.convertScheduleToScheduleDTO(scheduleService.createSchedule(schedule,
                scheduleDTO.getPetIds(), scheduleDTO.getEmployeeIds()));

        return newScheduleDTO;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {

        List<ScheduleDTO> scheduleDTO = this.convertListSchedulesToDTOS(scheduleService.getAllSchedules());

        return scheduleDTO;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {

        return this.convertListSchedulesToDTOS(scheduleService.getPetSchedules(petId));
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {

        return this.convertListSchedulesToDTOS(scheduleService.getEmployeeSchedules(employeeId));
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {

        return this.convertListSchedulesToDTOS(scheduleService.getCustomerSchedules(customerId));
    }

    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule) {

        ScheduleDTO scheduleDTO;
        List<Long> employeeIds = new ArrayList<>();
        List<Long> petIds = new ArrayList<>();

        for(Employee employee : schedule.getEmployees()) {
            employeeIds.add(employee.getId());
        }

        for(Pet pet : schedule.getPets()) {
            petIds.add(pet.getId());
        }

        scheduleDTO = new ScheduleDTO(schedule.getId(), employeeIds, petIds,
                schedule.getDate(), schedule.getActivities());

        return scheduleDTO;
    }

    private List<ScheduleDTO> convertListSchedulesToDTOS(List<Schedule> schedules) {

        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();

        for(Schedule schedule : schedules) {
            scheduleDTOS.add(convertScheduleToScheduleDTO(schedule));
        }

        return scheduleDTOS;
    }
}
