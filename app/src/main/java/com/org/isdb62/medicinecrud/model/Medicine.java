package com.org.isdb62.medicinecrud.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Medicine {

    private int id;
    private String name;
    private String generic;     //android
    private String type;
    private int quantity;    //android
    private BigDecimal price;
//    private Date expDate;


}
