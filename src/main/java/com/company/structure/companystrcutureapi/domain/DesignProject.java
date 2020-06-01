package com.company.structure.companystrcutureapi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DesignProject extends Project {

    private String developmentTechnology;

    @Builder
    public DesignProject(Integer id, String name, List<Employee> employees) {
        super(id, name, employees);
    }
}
