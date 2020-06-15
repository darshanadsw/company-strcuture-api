package com.company.structure.companystrcutureapi.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectVO {

    private Integer id;

    private String name;

    @Builder.Default
    private List<EmployeeVO> employees = new ArrayList<>();

    private String developmentTechnology;

    private String automationTechnology;

}
