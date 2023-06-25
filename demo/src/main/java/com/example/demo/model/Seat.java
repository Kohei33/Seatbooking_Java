package com.example.demo.model;


import java.math.BigInteger;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Seat {
    private BigInteger id;

    private String name;

    private LocalDateTime create_date;

    private LocalDateTime update_date;

}
