package com.example.demo.repository;

import java.sql.Date;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.Schedule;

@Mapper
public interface ScheduleRepository {
    Integer countBySeatAndDate(String seatname, Date date);
    Schedule findBySeatAndDate(String seatname, Date date);
    void save(Schedule schedule);
}
