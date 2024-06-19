package com.sptp.dawnary.schedule.service;

import com.sptp.dawnary.global.exception.MemberNotFoundException;
import com.sptp.dawnary.global.util.MemberInfo;
import com.sptp.dawnary.member.domain.Member;
import com.sptp.dawnary.member.repository.MemberRepository;
import com.sptp.dawnary.schedule.domain.Schedule;
import com.sptp.dawnary.global.exception.ScheduleNotFoundException;
import com.sptp.dawnary.schedule.repository.ScheduleRepository;
import com.sptp.dawnary.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.http.HttpRequest;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;

    private final JwtUtil jwtUtil;

    // 스케줄 등록
    public Schedule saveSchedule(Schedule schedule) {
        Member member = getMember();
        schedule.setMember(member);
        Schedule result = scheduleRepository.save(schedule);
        return result;
    };

    // 스케줄 수정
    public boolean updateSchedule(Long scheduleId, Schedule schedule) {
        Member member = getMember();
        schedule.setMember(member);
        if (scheduleRepository.existsById(scheduleId)) {
            schedule.setId(scheduleId);
            scheduleRepository.save(schedule);
            return true;
        } else {
            return false;
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
    public List<Schedule> findAllSchedules() {
        Long memberId = MemberInfo.getMemberId();
        List<Schedule> schedules = scheduleRepository.findAllOrderByDateDesc(memberId);
        return schedules;
    }

    // 특정 스케줄 조회
    public Schedule findSchedule(Long scheduleId) {
        Optional<Schedule> schedule = scheduleRepository.findById(scheduleId);
        if(schedule.isPresent()) {
            return schedule.get();
        }
        throw new ScheduleNotFoundException("존재하지 않는 스케줄 입니다.");
    }

    private Member getMember() {
        Long memberId = MemberInfo.getMemberId();
        Optional<Member> member = memberRepository.findById(memberId);
        if(member.isEmpty()) throw new MemberNotFoundException("멤버가 존재하지 않습니다.");
        return member.get();
    }
}
