package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.Seat;

@Mapper
public interface SeatRepository {    
    List<Seat> findAll();
}
