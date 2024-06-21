package com.sptp.dawnary.schedule.controller;

import com.sptp.dawnary.schedule.dto.ScheduleDto;
import com.sptp.dawnary.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 모든 스케줄 조회
    @GetMapping
    public ResponseEntity<?> getAllSchedules() {
        List<ScheduleDto> scheduleList = scheduleService.findAllSchedules();
        return new ResponseEntity<>(scheduleList, HttpStatus.OK);
    }

    // 특정 스케줄 조회
    @GetMapping("/{scheduleId}")
    public ResponseEntity<?> getSchedule(@PathVariable("scheduleId") Long scheduleId) {
        ScheduleDto schedule = scheduleService.findSchedule(scheduleId);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    // 스케줄 등록
    @PostMapping
    public ResponseEntity<?> saveSchedule(@RequestBody ScheduleDto scheduleDto) {
        ScheduleDto result = scheduleService.saveSchedule(scheduleDto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    // 특정 스케줄 수정
    @PutMapping("/{scheduleId}")
    public ResponseEntity<?> modifySchedule(@PathVariable("scheduleId") Long scheduleId, @RequestBody ScheduleDto scheduleDto) {
        ScheduleDto updatedSchedule = scheduleService.updateSchedule(scheduleId, scheduleDto);
        return new ResponseEntity<>(updatedSchedule, HttpStatus.OK);
    }

    // 특정 스케줄 삭제
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<?> removeSchedule(@PathVariable("scheduleId") Long scheduleId) {
        boolean result = scheduleService.deleteSchedule(scheduleId);

        if(result) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
