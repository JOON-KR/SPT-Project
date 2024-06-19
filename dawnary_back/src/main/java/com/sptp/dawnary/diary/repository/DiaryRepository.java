package com.sptp.dawnary.diary.repository;

import java.util.List;

import com.sptp.dawnary.diary.dto.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository <Diary, Long> {

    List<Diary> findByMemberId(Long memberId);
}
