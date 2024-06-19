package com.sptp.dawnary.diary.repository;

import com.sptp.dawnary.diary.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface DiaryRepository extends JpaRepository<Diary, Long> {

    List<Diary> findByMemberId(Long memberId);
}
