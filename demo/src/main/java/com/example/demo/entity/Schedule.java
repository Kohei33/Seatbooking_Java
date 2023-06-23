package com.example.demo.entity;

import java.math.BigInteger;
import java.sql.Date;

import lombok.Data;

@Data
public class Schedule {
    private BigInteger id;

    private Date date;
    
    private String username;
    
    private String seatname;

    private Date create_date;

    private Date update_date;

    public Schedule(){}

    public Schedule(Date date, String username, String seatname){
        super();
        this.date = date;
        this.username = username;
        this.seatname = seatname;
    }

}
