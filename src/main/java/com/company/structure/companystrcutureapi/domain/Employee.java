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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@NamedQueries({
    @NamedQuery(name = Employee.DELETE_QUERY, query = "DELETE FROM Employee e WHERE e.id =:id")
})
public class Employee {

    public final static String DELETE_QUERY = "deleteEmp";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private Long salary;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "employee",orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Phone> phones = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;

    @OneToOne(orphanRemoval = true)
    private Address address;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "employee_project", joinColumns = @JoinColumn(name = "employee_id"),
    inverseJoinColumns = @JoinColumn(name = "project_id"))
    @Builder.Default
    private List<Project> projects = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST},mappedBy = "manager", orphanRemoval = true,fetch = FetchType.LAZY)
    @Builder.Default
    private List<Employee> directs = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Employee manager;

    public void addPhone(Phone phone){
        phone.setEmployee(this);
        phones.add(phone);
    }
    public void removePhone(Phone phone){
        phone.setEmployee(null);
        phones.remove(phone);
    }

    public void addEmployeeToManager(Employee employee){
        employee.setManager(this);
        this.directs.add(employee);
    }
    public void removeRemoveEmployeeFromManager(Employee employee){
        employee.setManager(null);
        this.directs.remove(employee);
    }

    public void addProject(Project project){
        if(!projects.contains(project)){
            projects.add(project);
        }
        if(!project.getEmployees().contains(this)){
            project.getEmployees().add(this);
        }
    }

    public void removeProject(Project project){
        if(projects.contains(project)){
            projects.remove(project);
        }
        if(project.getEmployees().contains(this)){
            project.getEmployees().remove(this);
        }
    }

    public void addManager(Employee manager){
        if(manager.getManager() == null){
            this.setManager(manager);
            manager.getDirects().add(this);
        }
    }

}
