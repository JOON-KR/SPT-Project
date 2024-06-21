package com.sptp.dawnary.schedule.controller;

import com.sptp.dawnary.schedule.dto.ScheduleRequest;
import com.sptp.dawnary.schedule.dto.ScheduleResponse;
import com.sptp.dawnary.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 모든 스케줄 조회
    @GetMapping
    public ResponseEntity<?> getAllSchedules() {
        List<ScheduleResponse> scheduleList = scheduleService.findAllSchedules();
        return new ResponseEntity<>(scheduleList, HttpStatus.OK);
    }

    // 특정 스케줄 조회
    @GetMapping("/{scheduleId}")
    public ResponseEntity<?> getSchedule(@PathVariable("scheduleId") Long scheduleId) {
        ScheduleResponse schedule = scheduleService.findSchedule(scheduleId);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    // 스케줄 등록
    @PostMapping
    public ResponseEntity<?> saveSchedule(@RequestBody ScheduleRequest scheduleDto) {
        ScheduleResponse savedSchedule = scheduleService.saveSchedule(scheduleDto);
        return new ResponseEntity<>(savedSchedule, HttpStatus.CREATED);
    }

    // 특정 스케줄 수정
    @PutMapping("/{scheduleId}")
    public ResponseEntity<?> modifySchedule(@PathVariable("scheduleId") Long scheduleId, @RequestBody ScheduleRequest scheduleDto) {
        ScheduleResponse updatedSchedule = scheduleService.updateSchedule(scheduleId, scheduleDto);
        return new ResponseEntity<>(updatedSchedule, HttpStatus.OK);
    }

    // 특정 스케줄 삭제
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<?> removeSchedule(@PathVariable("scheduleId") Long scheduleId) {
        boolean result = scheduleService.deleteSchedule(scheduleId);
        if(result) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
