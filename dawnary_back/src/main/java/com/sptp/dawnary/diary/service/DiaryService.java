package com.sptp.dawnary.diary.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.sptp.dawnary.diary.domain.Diary;
import com.sptp.dawnary.diary.dto.DiaryDto;
import com.sptp.dawnary.global.exception.DiaryNotFoundException;
import com.sptp.dawnary.diary.repository.DiaryRepository;
import com.sptp.dawnary.global.exception.MemberNotFoundException;
import com.sptp.dawnary.global.util.MemberInfo;
import com.sptp.dawnary.member.domain.Member;
import com.sptp.dawnary.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DiaryService {

    private final DiaryRepository diaryRepository;

    private final MemberRepository memberRepository;

    // 다이어리 등록
    public Diary saveDiary(Diary diary) {
        diary.setMember(getMember());
        Diary result = diaryRepository.save(diary);
        return result;
    };

    // 다이어리 수정
    public boolean updateDiary(Long diaryId, Diary diary) {
        diary.setMember(getMember());
        if (diaryRepository.existsById(diaryId)) {
            diary.setId(diaryId);
            diaryRepository.save(diary);
            return true;
        }

        return false;

    }

    // 다이어리 삭제
    public boolean deleteDiary(Long diaryId) {
        if (diaryRepository.existsById(diaryId)) {
            diaryRepository.deleteById(diaryId);
            return true;
        } else {
            return false;
        }
    }

    // 다이어리 목록 조회
    public List<DiaryDto> findAllDiaries(Long memberId) {
        List<Diary> diaries = diaryRepository.findByMemberId(memberId);
        return diaries.stream()
                .map(DiaryDto::toDto)
                .collect(Collectors.toList());
    }

    // 특정 다이어리 조회
    public DiaryDto findDiary(Long diaryId) {
        Optional<Diary> diary = diaryRepository.findById(diaryId);
        if(diary.isPresent()) {
            return DiaryDto.toDto(diary.get());
        }
        throw new DiaryNotFoundException("존재하지 않는 다이어리 입니다.");
    }

    private Member getMember() {
        Long memberId = MemberInfo.getMemberId();
        Optional<Member> member = memberRepository.findById(memberId);
        if(member.isEmpty()) throw new MemberNotFoundException("멤버가 존재하지 않습니다.");
        return member.get();
    }
}
