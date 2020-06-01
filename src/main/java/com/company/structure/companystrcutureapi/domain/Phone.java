package com.company.structure.companystrcutureapi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String number;

    @Enumerated(EnumType.STRING)
    private PhoneType type;

    @ManyToOne
    private Employee employee;

}
