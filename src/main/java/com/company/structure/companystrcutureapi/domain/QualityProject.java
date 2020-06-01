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
public class QualityProject extends Project {

    private String automationTechnology;

    @Builder
    public QualityProject(Integer id, String name, List<Employee> employees) {
        super(id, name, employees);
    }
}
