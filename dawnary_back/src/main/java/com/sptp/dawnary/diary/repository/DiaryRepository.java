package com.sptp.dawnary.diary.repository;

import com.sptp.dawnary.diary.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    List<Diary> findByMemberId(Long memberId);

    @Query("SELECT d FROM Diary d WHERE d.member.id = :memberId AND d.status = 1")
    List<Diary> findOtherUserDiaries(Long memberId);
}
