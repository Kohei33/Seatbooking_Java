package com.example.demo.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Schedule {
    private BigInteger id;

    private LocalDate date;
    
    private String username;
    
    private String seatname;

    private LocalDateTime create_date;

    private LocalDateTime update_date;

    public Schedule(){}

    public Schedule(LocalDate date, String username, String seatname){
        super();
        this.date = date;
        this.username = username;
        this.seatname = seatname;
    }

}
