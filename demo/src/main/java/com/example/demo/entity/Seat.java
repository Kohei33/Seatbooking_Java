package com.example.demo.entity;


import java.math.BigInteger;
import java.sql.Date;

import lombok.Data;

@Data
public class Seat {
    private BigInteger id;

    private String name;

    private Date create_date;

    private Date update_date;

}
