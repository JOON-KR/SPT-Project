package com.sptp.dawnary.schedule.controller;

import java.util.List;

import com.sptp.dawnary.schedule.domain.Schedule;
import com.sptp.dawnary.schedule.dto.ScheduleDto;
import com.sptp.dawnary.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    private final ModelMapper modelMapper;

    // 모든 스케줄 조회
    @GetMapping
    public ResponseEntity<?> getAllSchedules() {
        List<Schedule> scheduleList = scheduleService.findAllSchedules();
        return new ResponseEntity<>(scheduleList, HttpStatus.OK);
    }

    // 특정 스케줄 조회
    @GetMapping("/{scheduleId}")
    public ResponseEntity<?> getSchedule(@PathVariable("scheduleId") Long scheduleId) {
        Schedule schedule = scheduleService.findSchedule(scheduleId);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    // 스케줄 등록
    @PostMapping
    public ResponseEntity<?> saveSchedule(@RequestBody ScheduleDto schedule) {
        Schedule entity = modelMapper.map(schedule, Schedule.class);
        Schedule result = scheduleService.saveSchedule(entity);

        if(result == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(HttpStatus.OK);
    }


   // 특정 스케줄 수정
    @PutMapping("/{scheduleId}")
    public ResponseEntity<?> modifySchedule(@PathVariable("scheduleId") Long scheduleId, @RequestBody ScheduleDto schedule) {
        Schedule entity = modelMapper.map(schedule, Schedule.class);

        boolean result = scheduleService.updateSchedule(scheduleId, entity);

        if(result) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // 특정 스케줄 삭제
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<?> removeSchedule(@PathVariable("scheduleId") Long scheduleId) {
        boolean result = scheduleService.deleteSchedule(scheduleId);

        if(result) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}