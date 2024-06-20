package com.sptp.dawnary.schedule.service;

import com.sptp.dawnary.global.exception.MemberNotFoundException;
import com.sptp.dawnary.global.exception.ScheduleNotFoundException;
import com.sptp.dawnary.global.util.MemberInfo;
import com.sptp.dawnary.location.domain.Location;
import com.sptp.dawnary.location.repository.LocationRepository;
import com.sptp.dawnary.member.domain.Member;
import com.sptp.dawnary.member.repository.MemberRepository;
import com.sptp.dawnary.schedule.domain.Schedule;
import com.sptp.dawnary.schedule.dto.ScheduleDto;
import com.sptp.dawnary.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;
    private final LocationRepository locationRepository;

    private final ModelMapper modelMapper;

    // 스케줄 등록
    public ScheduleDto saveSchedule(ScheduleDto scheduleDto) {
        Member member = getMember();
        Schedule schedule = modelMapper.map(scheduleDto, Schedule.class);
        schedule.setMember(member);
        schedule.setLocation(null);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        Location location = modelMapper.map(scheduleDto.getLocation(), Location.class);
        location.setSchedule(savedSchedule);

        Location savedLocation = locationRepository.save(location);
        savedSchedule.setLocation(savedLocation);
        scheduleRepository.save(savedSchedule);

        return modelMapper.map(savedSchedule, ScheduleDto.class);
    }

    // 스케줄 수정
    public ScheduleDto updateSchedule(Long scheduleId, ScheduleDto scheduleDto) {
        Member member = getMember();
        Schedule schedule = modelMapper.map(scheduleDto, Schedule.class);
        schedule.setMember(member);
        if (scheduleRepository.existsById(scheduleId)) {
            schedule.setId(scheduleId);
            Schedule updatedSchedule = scheduleRepository.save(schedule);
            return modelMapper.map(updatedSchedule, ScheduleDto.class);
        } else {
            throw new ScheduleNotFoundException("존재하지 않는 스케줄 입니다.");
        }
    }

    // 스케줄 삭제
    public boolean deleteSchedule(Long scheduleId) {
        if (scheduleRepository.existsById(scheduleId)) {
            scheduleRepository.deleteById(scheduleId);
            return true;
        } else {
            return false;
        }
    }

    // 스케줄 목록 조회
    public List<ScheduleDto> findAllSchedules() {
        Long memberId = MemberInfo.getMemberId();
        List<Schedule> schedules = scheduleRepository.findAllOrderByDateDesc(memberId);
        return schedules.stream()
                .map(schedule -> modelMapper.map(schedule, ScheduleDto.class))
                .collect(Collectors.toList());
    }

    // 특정 스케줄 조회
    public ScheduleDto findSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException("존재하지 않는 스케줄 입니다."));
        return modelMapper.map(schedule, ScheduleDto.class);
    }

    private Member getMember() {
        Long memberId = MemberInfo.getMemberId();
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("멤버가 존재하지 않습니다."));
    }
}
