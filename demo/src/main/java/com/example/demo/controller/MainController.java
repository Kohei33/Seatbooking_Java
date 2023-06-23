package com.example.demo.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Schedule;
import com.example.demo.entity.Seat;
import com.example.demo.form.ReserveForm;
import com.example.demo.repository.ScheduleRepository;
import com.example.demo.repository.SeatRepository;
import com.example.demo.service.CreateCalendarService;

@Controller
public class MainController {

    @Autowired
    SeatRepository seatRepository;
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    CreateCalendarService createCalendarService;

    // 初期表示
    @RequestMapping(value="/", method=RequestMethod.GET)
    public String showTop(Model model, ReserveForm reserveForm) {
        Calendar calendar = Calendar.getInstance();
        Date today = new Date(calendar.getTime().getTime());
        List<Date> days = new ArrayList<>();
        Iterable<Seat> seats;

        // 表示する日付
        days = createCalendarService.getDatesToShow(today);
        model.addAttribute("days", days);

        // 表示する席
        seats = createCalendarService.getSeatsToShow();
        model.addAttribute("seats", seats);

        // 日付×席の予約可否
        TreeMap<String, TreeMap<String, String>> map1 = createCalendarService.getAvailableOrNot(seats, days, today);
        model.addAttribute("map1", map1);
        model.addAttribute("reserveForm", reserveForm);

        return "top";
    }

    // 以前の日付表示時
    @RequestMapping(value="/toBefore", method=RequestMethod.POST)
    public String showTopToBefore(Model model, ReserveForm reserveForm, @RequestParam(name="baseDate") Date baseDate) {
        Calendar calendar = Calendar.getInstance();
        Date startDate;
        Date today = new Date(calendar.getTime().getTime());
        List<Date> days = new ArrayList<>();
        Iterable<Seat> seats;

        // 表示する日付の開始日を取得
        Calendar baseDateCalendar = Calendar.getInstance();
        baseDateCalendar.setTime(baseDate);
        baseDateCalendar.add(Calendar.DAY_OF_MONTH, -7);
        Calendar compareCalendar = Calendar.getInstance();
        // 60日前まで表示可能
        compareCalendar.add(Calendar.DAY_OF_MONTH, -60);
        if (baseDateCalendar.compareTo(compareCalendar) < 0){
            startDate = new Date(compareCalendar.getTime().getTime());
            model.addAttribute("message", "You can check maximum 60 days ago.");
        } else {
            startDate = new Date(baseDateCalendar.getTime().getTime());
        };

        // 表示する日付
        days = createCalendarService.getDatesToShow(startDate);
        model.addAttribute("days", days);

        // 表示する席
        seats = createCalendarService.getSeatsToShow();
        model.addAttribute("seats", seats);

        // 日付×席の予約可否
        TreeMap<String, TreeMap<String, String>> map1 = createCalendarService.getAvailableOrNot(seats, days, today);
        model.addAttribute("map1", map1);
        model.addAttribute("reserveForm", reserveForm);

        return "top";
    }

    // 以前の日付表示時
    @RequestMapping(value="/toAfter", method=RequestMethod.POST)
    public String showTopToAfter(Model model, ReserveForm reserveForm, @RequestParam(name="baseDate") Date baseDate) {
        Calendar calendar = Calendar.getInstance();
        Date startDate;
        Date today = new Date(calendar.getTime().getTime());
        List<Date> days = new ArrayList<>();
        Iterable<Seat> seats;

        // 表示する日付の開始日を取得
        Calendar baseDateCalendar = Calendar.getInstance();
        baseDateCalendar.setTime(baseDate);
        baseDateCalendar.add(Calendar.DAY_OF_MONTH, 1);
        Calendar compareCalendar = Calendar.getInstance();
        // 30日後まで表示可能
        compareCalendar.add(Calendar.DAY_OF_MONTH, 23);
        if (baseDateCalendar.compareTo(compareCalendar) > 0){
            startDate = new Date(compareCalendar.getTime().getTime());
            model.addAttribute("message", "You can check maximum 30 days later.");
        } else {
            startDate = new Date(baseDateCalendar.getTime().getTime());
        };

        // 表示する日付
        days = createCalendarService.getDatesToShow(startDate);
        model.addAttribute("days", days);

        // 表示する席
        seats = createCalendarService.getSeatsToShow();
        model.addAttribute("seats", seats);

        // 日付×席の予約可否
        TreeMap<String, TreeMap<String, String>> map1 = createCalendarService.getAvailableOrNot(seats, days, today);
        model.addAttribute("map1", map1);
        model.addAttribute("reserveForm", reserveForm);

        return "top";
    }

    // 予約実行時
    @RequestMapping(value="/book", method=RequestMethod.POST)
    public String doReserve(ReserveForm reserveForm){
        String[] checklist = reserveForm.getChecklist();
        
        for (int i = 0;i<checklist.length;i++){
            int commaindex = checklist[i].lastIndexOf('/');
            String datetext = checklist[i].substring(commaindex + 1);
            String username = "testuser";
            String seatname = checklist[i].substring(0, commaindex);
            
            Date date = Date.valueOf(datetext);
            Schedule schedule = new Schedule(date, username, seatname);

            scheduleRepository.save(schedule);

        }
        return "redirect:/";

    }
}
