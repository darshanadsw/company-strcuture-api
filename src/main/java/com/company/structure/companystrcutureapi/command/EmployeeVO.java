package com.company.structure.companystrcutureapi.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class EmployeeVO {

    private Integer id;

    @NotNull
    @NotBlank
    @Size(min = 1,max = 50)
    private String name;

    @NotNull
    @Max(999999)
    private Long salary;

    @Builder.Default
    private List<ProjectVO> projects = new ArrayList<>();

    private DepartmentVO department;

    @Builder.Default
    private List<PhoneVO> phones = new ArrayList<>();

    private EmployeeVO manager;

    @Builder.Default
    private List<EmployeeVO> directs = new ArrayList<>();

}
