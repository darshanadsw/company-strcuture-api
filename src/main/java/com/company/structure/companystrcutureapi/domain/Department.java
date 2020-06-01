package com.company.structure.companystrcutureapi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "department",orphanRemoval = true)
    private List<Employee> employees = new ArrayList<>();

    public void addEmployee(Employee employee){
        employee.setDepartment(this);
        employees.add(employee);
    }

    public void removeEmployee(Employee employee){
        employee.setDepartment(null);
        employees.remove(employee);
    }
}
