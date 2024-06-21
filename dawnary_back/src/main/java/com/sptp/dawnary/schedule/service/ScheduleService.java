package com.sptp.dawnary.schedule.service;

import com.sptp.dawnary.global.exception.MemberNotFoundException;
import com.sptp.dawnary.global.exception.ScheduleNotFoundException;
import com.sptp.dawnary.global.util.MemberInfo;
import com.sptp.dawnary.location.domain.Location;
import com.sptp.dawnary.location.repository.LocationRepository;
import com.sptp.dawnary.member.domain.Member;
import com.sptp.dawnary.member.repository.MemberRepository;
import com.sptp.dawnary.schedule.domain.Schedule;
import com.sptp.dawnary.schedule.dto.ScheduleRequest;
import com.sptp.dawnary.schedule.dto.ScheduleResponse;
import com.sptp.dawnary.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;
    private final LocationRepository locationRepository;

    // 스케줄 등록
    public ScheduleResponse saveSchedule(ScheduleRequest scheduleRequest) {
        Member member = getMember();
        Schedule schedule = Schedule.toEntity(scheduleRequest);
        schedule.setMember(member);
        schedule.setLocation(null);

        scheduleRepository.save(schedule);

        Location location = Location.toEntity(scheduleRequest.locationRequest());
        location.setSchedule(schedule);

        Location savedLocation = locationRepository.save(location);
        schedule.setLocation(savedLocation);
        scheduleRepository.save(schedule);

        return ScheduleResponse.toResponse(schedule);
    }

    // 스케줄 수정
    public ScheduleResponse updateSchedule(Long scheduleId, ScheduleRequest scheduleRequest) {

        Schedule schedule = Schedule.toEntity(scheduleRequest);
        schedule.setMember(getMember());

        if (scheduleRepository.existsById(scheduleId)) {
            schedule.setId(scheduleId);
            scheduleRepository.save(schedule);
            return ScheduleResponse.toResponse(schedule);
        }

        throw new ScheduleNotFoundException();

    }

    // 스케줄 삭제
    public boolean deleteSchedule(Long scheduleId) {
        if (scheduleRepository.existsById(scheduleId)) {
            scheduleRepository.deleteById(scheduleId);
            return true;
        }

        return false;

    }

    // 스케줄 목록 조회
    public List<ScheduleResponse> findAllSchedules() {
        Long memberId = MemberInfo.getMemberId();
        List<Schedule> schedules = scheduleRepository.findByMemberId(memberId);
        return schedules.stream()
                .map(ScheduleResponse::toResponse)
                .toList();
    }

    // 특정 스케줄 조회
    public ScheduleResponse findSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException());
        return ScheduleResponse.toResponse(schedule);
    }

    private Member getMember() {
        Long memberId = MemberInfo.getMemberId();
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException());
    }
}
