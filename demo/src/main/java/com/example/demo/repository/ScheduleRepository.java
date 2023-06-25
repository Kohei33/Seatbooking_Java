package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.Schedule;

@Mapper
public interface ScheduleRepository {
    Integer countBySeatAndDate(String seatname, LocalDate date);
    Schedule findBySeatAndDate(String seatname, LocalDate date);
    List<Schedule> findByUser(String username);
    void save(Schedule schedule);
    void delete(String seatname, String username, LocalDate date);
}
