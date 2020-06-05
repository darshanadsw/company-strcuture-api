package com.company.structure.companystrcutureapi.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchDTO {

    private String empName;
    private Long salary;
    private String projectName;
    private String cityName;
    private String departmentName;

}
