package com.sptp.dawnary.diary.service;

import java.util.List;
import java.util.Optional;

import com.sptp.dawnary.diary.domain.Diary;
import com.sptp.dawnary.exception.DiaryNotFoundException;
import com.sptp.dawnary.diary.repository.DiaryRepository;
import org.springframework.stereotype.Service;

@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    // 다이어리 등록
    public Diary saveDiary(Diary diary) {
        Diary result = diaryRepository.save(diary);
        return result;
    };

    // 다이어리 수정
    public boolean updateDiary(Long diaryId, Diary diary) {
        if (diaryRepository.existsById(diaryId)) {
            diary.setId(diaryId);
            diaryRepository.save(diary);
            return true;
        } else {
            return false;
        }
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
    public List<Diary> findAllDiaries(Long memberId) {
        List<Diary> diaries = diaryRepository.findByMemberId(memberId);
        return diaries;
    }

    // 특정 다이어리 조회
    public Diary findDiary(Long diaryId) {
        Optional<Diary> diary = diaryRepository.findById(diaryId);
        if(diary.isPresent()) {
            return diary.get();
        }

        throw new DiaryNotFoundException("존재하지 않는 다이어리 입니다.");
    }
}
