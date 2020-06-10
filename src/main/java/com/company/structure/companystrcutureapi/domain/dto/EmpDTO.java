package com.company.structure.companystrcutureapi.domain.dto;

import com.company.structure.companystrcutureapi.domain.Department;
import com.company.structure.companystrcutureapi.domain.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmpDTO {

    private Employee employee;
    private Department department;
    private Integer id;
    private String name;
    private Long salary;
    private String departmentName;

    public EmpDTO(Employee employee, Department department) {
        this.employee = employee;
        this.department = department;
    }

    public EmpDTO(Integer id,String name){
        this.id = id;
        this.name = name;
    }

    public EmpDTO(Integer id,String name,Long salary,Department department, Employee manager){
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.department = department;
        this.employee = manager;
    }

    public EmpDTO(Integer id, String name, Long salary,String departmentName) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.departmentName = departmentName;
    }
}
