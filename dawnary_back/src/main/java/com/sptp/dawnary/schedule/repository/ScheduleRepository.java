package com.sptp.dawnary.schedule.repository;

import com.sptp.dawnary.schedule.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository <Schedule, Long> {

    @Query("SELECT s FROM Schedule s WHERE s.member.id = :memberId ORDER BY s.date DESC")
    List<Schedule> findAllOrderByDateDesc(@Param("memberId") Long memberId);
}
