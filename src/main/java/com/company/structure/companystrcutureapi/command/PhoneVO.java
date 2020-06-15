package com.company.structure.companystrcutureapi.command;

import com.company.structure.companystrcutureapi.domain.PhoneType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhoneVO {

    private Integer id;

    private String number;

    private PhoneType phoneType;

    private EmployeeVO employee;

}
