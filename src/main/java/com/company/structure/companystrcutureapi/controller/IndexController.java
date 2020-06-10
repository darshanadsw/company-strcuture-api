package com.company.structure.companystrcutureapi.controller;

import com.company.structure.companystrcutureapi.domain.Employee;
import com.company.structure.companystrcutureapi.domain.dto.EmpDTO;
import com.company.structure.companystrcutureapi.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class IndexController {

    private final EmployeeService employeeService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getIndexApi(){
        return ResponseEntity.ok().body("{timestamp : " + LocalDateTime.now()
            .format(DateTimeFormatter.ISO_DATE_TIME) +"}");
    }

    @GetMapping("/employees")
    public ResponseEntity<?> getEmployeeInfo(){
        return ResponseEntity.ok()
            .body(employeeService.getAllEmployees());
    }

    @PostMapping("/employees")
    public ResponseEntity<?> createEmployee(@ModelAttribute Employee employee){
        employeeService.saveEmployee(employee);
        return ResponseEntity
            .created(URI.create("api/employees/"+employee.getId())).build();
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<EmpDTO> findEmpById(@PathVariable Integer id){
        return ResponseEntity.ok(employeeService.findEmpById(id));
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id){
        employeeService.deleteEmployee(id);
        return ResponseEntity.accepted().build();
    }


}
