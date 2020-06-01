package com.company.structure.companystrcutureapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity

@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;

    @ManyToMany
    @JoinTable(name = "employee_project",joinColumns = @JoinColumn(name = "project_id"),
    inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private List<Employee> employees = new ArrayList<>();

    public void addEmployee(Employee employee){
        if(!this.employees.contains(employee)){
            this.employees.add(employee);
        }
        if(!employee.getProjects().contains(this)){
            employee.getProjects().add(this);
        }
    }

    public void removeEmployee(Employee employee){
        if(this.employees.contains(employee)){
            this.employees.remove(employee);
        }
        if(employee.getProjects().contains(this)){
            employee.getProjects().remove(this);
        }
    }

}
