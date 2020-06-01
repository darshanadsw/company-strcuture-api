package com.company.structure.companystrcutureapi.domain.dto;

import com.company.structure.companystrcutureapi.domain.Department;
import com.company.structure.companystrcutureapi.domain.Employee;
import lombok.Getter;

@Getter
public class EmpDTO {
    private Employee employee;
    private Department department;

    public EmpDTO(Employee employee, Department department) {
        this.employee = employee;
        this.department = department;
    }

}
