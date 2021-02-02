package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.exceptions.NoDataFoundForRequest;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Allows us to find our employee by ID.
    public Employee getEmployeeByID(long id) {
        String message = String.format("No Employee id %s found.", id);
        return this.employeeRepository.findById(id).orElseThrow(() -> new NoDataFoundForRequest(message));
    }

    // Allows us to save our employee
    public Employee save(Employee employee) {
        return this.employeeRepository.save(employee);
    }

    // Return a set of data where skills by date match what is requested by controller.
    public List<Employee> getEmployeeBySkillAndDate(Set<EmployeeSkill> skills, LocalDate date) {

        // Using local timezone of date we will set the day in
        // dayOfWeek variable. Example: value could be "SUNDAY".
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        // Empty array to populate our employees.
        List<Employee> employees = new ArrayList<>();

        // Loop by skills
        for(EmployeeSkill skill : skills) {
            // Add skills to employeeSet
            List<Employee> employeeSet = this.employeeRepository.getAllBySkills(skill);

            // Add skills to employees list if it does not contain the employee.
            // And employee days available matches dayOfWeek.
            // And employee has the skill requested.
            for (Employee employee : employeeSet) {
                if (!employees.contains(employee) && employee.getDaysAvailable().contains(dayOfWeek) &&
                        employee.getSkills().containsAll(skills)) {
                    employees.add(employee);
                }
            }
        }
        return employees;
    }

    // We need to have days for our employees accessibility with a built in calender.
    // Here we can set this availability pragmatically.
    public void setEmployeeAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee employee = this.getEmployeeByID(employeeId);
        employee.setDaysAvailable(daysAvailable);
        this.save(employee);
    }
}
