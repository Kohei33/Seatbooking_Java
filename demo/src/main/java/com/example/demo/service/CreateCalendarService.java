package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Schedule;
import com.example.demo.model.Seat;
import com.example.demo.repository.ScheduleRepository;
import com.example.demo.repository.SeatRepository;

@Service
public class CreateCalendarService {
    @Autowired
    SeatRepository seatRepository;
    @Autowired
    ScheduleRepository scheduleRepository;
    
    // 表示する日付
    public List<LocalDate> getDatesToShow(LocalDate startDate){
        List<LocalDate> days = new ArrayList<>();
        days.add(startDate);
        for (int i = 1; i < 7; i++){
            days.add(startDate.plusDays(i));
        }
        return days;
    }

    // 表示する席
    public Iterable<Seat> getSeatsToShow(){
        Iterable<Seat> seats = seatRepository.findAll();
        return seats;
    }

    // 日付×席の予約可否
    public TreeMap<String, TreeMap<String, String>> getAvailableOrNot(Iterable<Seat> seats, List<LocalDate> days, LocalDate today){
        TreeMap<String, TreeMap<String, String>> map1 = new TreeMap<>();
        for(Seat seat : seats){
            TreeMap<String, String> map2 = new TreeMap<>();
            for(LocalDate day : days){
                if (scheduleRepository.countBySeatAndDate(seat.getName(), day) == 0){
                    if (day.compareTo(today) >= 0){
                        map2.put(day.toString(), "○");
                    } else {
                        map2.put(day.toString(), "×");
                    }
                } else {
                    Schedule schedule = scheduleRepository.findBySeatAndDate(seat.getName(), day);
                    map2.put(day.toString(), schedule.getUsername());
                }
                map1.put(seat.getName(), map2);
            }
        }
        return map1;
    }
}
