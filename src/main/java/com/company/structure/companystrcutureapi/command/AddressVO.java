package com.company.structure.companystrcutureapi.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressVO {

    private Integer id;

    private String street;
    private String city;
    private String state;
    private String zip;
}
