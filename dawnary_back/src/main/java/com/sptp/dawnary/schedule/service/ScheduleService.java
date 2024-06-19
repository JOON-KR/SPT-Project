package com.sptp.dawnary.schedule.service;

import com.sptp.dawnary.schedule.domain.Schedule;
import com.sptp.dawnary.exception.ScheduleNotFoundException;
import com.sptp.dawnary.schedule.repository.ScheduleRepository;

import java.util.List;
import java.util.Optional;

public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    // 스케줄 등록
    public Schedule saveSchedule(Schedule schedule) {
        Schedule result = scheduleRepository.save(schedule);
        return result;
    };

    // 스케줄 수정
    public boolean updateSchedule(Long scheduleId, Schedule schedule) {
        if (scheduleRepository.existsById(scheduleId)) {
            schedule.setId(scheduleId);
            scheduleRepository.save(schedule);
            return true;
        } else {
            return false;
        }
    }

    // 스케줄 삭제
    public boolean deleteDiary(Long scheduleId) {
        if (scheduleRepository.existsById(scheduleId)) {
            scheduleRepository.deleteById(scheduleId);
            return true;
        } else {
            return false;
        }
    }

    // 스케줄 목록 조회
    public List<Schedule> findAllSchedules(Long memberId) {
        List<Schedule> schedules = scheduleRepository.findByMemberId(memberId);
        return schedules;
    }

    // 특정 스케줄 조회
    public Schedule findDiary(Long scheduleId) {
        Optional<Schedule> schedule = scheduleRepository.findById(scheduleId);
        if(schedule.isPresent()) {
            return schedule.get();
        }

        throw new ScheduleNotFoundException("존재하지 않는 스케줄 입니다.");
    }
}
