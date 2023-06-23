package com.example.demo.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Schedule;
import com.example.demo.entity.Seat;
import com.example.demo.repository.ScheduleRepository;
import com.example.demo.repository.SeatRepository;

@Service
public class CreateCalendarService {
    @Autowired
    SeatRepository seatRepository;
    @Autowired
    ScheduleRepository scheduleRepository;
    
    // 表示する日付
    public List<Date> getDatesToShow(Date startDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        List<Date> days = new ArrayList<>();
        Date endDate;
        days.add(startDate);
        for (int i = 1; i < 7; i++){
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            endDate = new Date(calendar.getTime().getTime());
            days.add(endDate);
        }
        return days;
    }

    // 表示する席
    public Iterable<Seat> getSeatsToShow(){
        Iterable<Seat> seats = seatRepository.findAll();
        return seats;
    }

    // 日付×席の予約可否
    public TreeMap<String, TreeMap<String, String>> getAvailableOrNot(Iterable<Seat> seats, List<Date> days, Date today){
        TreeMap<String, TreeMap<String, String>> map1 = new TreeMap<>();
        for(Seat seat : seats){
            TreeMap<String, String> map2 = new TreeMap<>();
            for(Date day : days){
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
