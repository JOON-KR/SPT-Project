package com.sptp.dawnary.follow.service;

import org.springframework.stereotype.Service;

import com.sptp.dawnary.follow.domain.Follow;
import com.sptp.dawnary.follow.repository.jpa.FollowRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowWriteService {

    private final FollowRepository followRepository;

    //팔로우
    public void follow(Follow follow) {
        followRepository.save(follow);
    }

    //언팔로우
    public void unfollow(Follow follow) {
        followRepository.delete(follow);
    }
}
