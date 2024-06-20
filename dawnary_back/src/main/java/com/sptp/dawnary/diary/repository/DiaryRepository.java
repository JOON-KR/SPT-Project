package com.sptp.dawnary.diary.repository;

import com.sptp.dawnary.diary.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

    List<Diary> findByMemberId(Long memberId);
}
