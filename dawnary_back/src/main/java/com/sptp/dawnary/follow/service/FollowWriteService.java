package com.sptp.dawnary.follow.service;

import org.springframework.stereotype.Service;

import com.sptp.dawnary.follow.domain.Follow;
import com.sptp.dawnary.follow.repository.jpa.FollowRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FollowWriteService {

    private final FollowRepository followRepository;

    //팔로우
    public void follow(Follow follow) {
        log.info("save follow {}", follow);
        followRepository.save(follow);
    }

    //언팔로우
    public void unfollow(Follow follow) {
        log.info("delete follow {}", follow);
        followRepository.delete(follow);
    }
}
