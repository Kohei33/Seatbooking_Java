package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.Seat;

@Mapper
public interface SeatRepository {    
    List<Seat> findAll();
}
