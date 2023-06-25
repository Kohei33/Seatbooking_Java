package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.CheckList;
import com.example.demo.model.Schedule;
import com.example.demo.model.Seat;
import com.example.demo.repository.ScheduleRepository;
import com.example.demo.repository.SeatRepository;
import com.example.demo.service.CreateCalendarService;
import com.example.demo.service.SessionService;

@Controller
public class MainController {

    @Autowired
    SeatRepository seatRepository;
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    CreateCalendarService createCalendarService;
    @Autowired
    SessionService sessionService;

    // 初期表示
    @RequestMapping(value="/", method=RequestMethod.GET)
    public String showTop(Model model, CheckList checkList) {
        // 画面遷移ボタン
        String myPageUrl = "http://localhost:8080/myPage";
        String homeUrl = "http://localhost:8080";
        model.addAttribute("myPageUrl", myPageUrl);
        model.addAttribute("homeUrl", homeUrl);

        List<LocalDate> days = new ArrayList<>();
        Iterable<Seat> seats;
        LocalDate today = LocalDate.now();

        // 表示する日付
        days = createCalendarService.getDatesToShow(today);
        model.addAttribute("days", days);

        // 表示する席
        seats = createCalendarService.getSeatsToShow();
        model.addAttribute("seats", seats);

        // 日付×席の予約可否
        TreeMap<String, TreeMap<String, String>> map1 = createCalendarService.getAvailableOrNot(seats, days, today);
        model.addAttribute("map1", map1);
        model.addAttribute("checkList", checkList);

        // ログインユーザ取得
        String loginUsername = sessionService.getLoginUsername();
        model.addAttribute("loginUsername", loginUsername);

        return "top";
    }

    // 以前の日付表示時
    @RequestMapping(value="/toBefore", method=RequestMethod.POST)
    public String showTopToBefore(Model model, CheckList checkList, @RequestParam(name="baseDate") LocalDate baseDate) {
        // 画面遷移ボタン
        String myPageUrl = "http://localhost:8080/myPage";
        String homeUrl = "http://localhost:8080";
        model.addAttribute("myPageUrl", myPageUrl);
        model.addAttribute("homeUrl", homeUrl);
        
        LocalDate startDate;
        LocalDate today = LocalDate.now();
        List<LocalDate> days = new ArrayList<>();
        Iterable<Seat> seats;

        // 表示する日付の開始日を取得
        baseDate = baseDate.minusDays(7);
        LocalDate borderDate = LocalDate.now();
        borderDate = borderDate.minusDays(60);
        // 60日前まで表示可能
        if (baseDate.compareTo(borderDate) < 0) {
            startDate = borderDate;
            model.addAttribute("message", "You can check maximum 60 days ago.");
        } else {
            startDate = baseDate;
        }

        // 表示する日付
        days = createCalendarService.getDatesToShow(startDate);
        model.addAttribute("days", days);

        // 表示する席
        seats = createCalendarService.getSeatsToShow();
        model.addAttribute("seats", seats);

        // 日付×席の予約可否
        TreeMap<String, TreeMap<String, String>> map1 = createCalendarService.getAvailableOrNot(seats, days, today);
        model.addAttribute("map1", map1);
        model.addAttribute("checkList", checkList);

        // ログインユーザ取得
        String loginUsername = sessionService.getLoginUsername();
        model.addAttribute("loginUsername", loginUsername);

        return "top";
    }

    // 以前の日付表示時
    @RequestMapping(value="/toAfter", method=RequestMethod.POST)
    public String showTopToAfter(Model model, CheckList checkList, @RequestParam(name="baseDate") LocalDate baseDate) {
        // 画面遷移ボタン
        String myPageUrl = "http://localhost:8080/myPage";
        String homeUrl = "http://localhost:8080";
        model.addAttribute("myPageUrl", myPageUrl);
        model.addAttribute("homeUrl", homeUrl);
        
        LocalDate startDate;
        LocalDate today = LocalDate.now();
        List<LocalDate> days = new ArrayList<>();
        Iterable<Seat> seats;

        // 表示する日付の開始日を取得
        baseDate = baseDate.plusDays(7);
        LocalDate borderDate = LocalDate.now();
        borderDate = borderDate.plusDays(23);
        // 30日後まで表示可能
        if (baseDate.compareTo(borderDate) > 0){
            startDate = borderDate;
            model.addAttribute("message", "You can check maximum 30 days later.");
        } else {
            startDate = baseDate;
        }

        // 表示する日付
        days = createCalendarService.getDatesToShow(startDate);
        model.addAttribute("days", days);

        // 表示する席
        seats = createCalendarService.getSeatsToShow();
        model.addAttribute("seats", seats);

        // 日付×席の予約可否
        TreeMap<String, TreeMap<String, String>> map1 = createCalendarService.getAvailableOrNot(seats, days, today);
        model.addAttribute("map1", map1);
        model.addAttribute("checkList", checkList);

        // ログインユーザ取得
        String loginUsername = sessionService.getLoginUsername();
        model.addAttribute("loginUsername", loginUsername);

        return "top";
    }

    // 予約実行時
    @RequestMapping(value="/book", method=RequestMethod.POST)
    public String doReserve(CheckList checkList){
        // ログインユーザ取得
        String loginUsername = sessionService.getLoginUsername();

        String[] reserveCheckList = checkList.getCheckList();
        
        for (int i = 0;i<reserveCheckList.length;i++){
            int commaindex = reserveCheckList[i].lastIndexOf('/');
            String datetext = reserveCheckList[i].substring(commaindex + 1);
            String username = loginUsername;
            String seatname = reserveCheckList[i].substring(0, commaindex);
            
            LocalDate date = LocalDate.parse(datetext);
            Schedule schedule = new Schedule(date, username, seatname);

            scheduleRepository.save(schedule);

        }
        return "redirect:/";

    }

    // マイページ
    @RequestMapping(value="/myPage", method=RequestMethod.GET)
    public String showMyPage(Model model, CheckList checkList) {
        // 画面遷移ボタン
        String homeUrl = "http://localhost:8080";
        model.addAttribute("homeUrl", homeUrl);

        // ログインユーザ取得
        String loginUsername = sessionService.getLoginUsername();
        model.addAttribute("loginUsername", loginUsername);

        List<Schedule> scheduleList = scheduleRepository.findByUser(loginUsername);
        model.addAttribute("scheduleList", scheduleList);
        model.addAttribute("checkList", checkList);

        return "myPage";
    }

    // 予約削除
    @RequestMapping(value="/myPage", method=RequestMethod.POST)
    public String doDelete(CheckList checkList){
        // ログインユーザ取得
        String loginUsername = sessionService.getLoginUsername();

        String[] deleteCheckList = checkList.getCheckList();
        
        for (int i = 0;i<deleteCheckList.length;i++){
            int commaindex = deleteCheckList[i].lastIndexOf('/');
            String datetext = deleteCheckList[i].substring(commaindex + 1);
            String username = loginUsername;
            String seatname = deleteCheckList[i].substring(0, commaindex);
            LocalDate date = LocalDate.parse(datetext);
            scheduleRepository.delete(seatname, username, date);

        }
        return "redirect:/myPage";

    }
}
