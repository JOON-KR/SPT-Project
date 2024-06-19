package com.sptp.dawnary.like.repository;

import com.sptp.dawnary.like.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository <Like, Long> {

    List<Like> findByMemberId(Long memberId);

    Optional<Like> findByMemberIdAndSeriesId(Long memberId, Long seriesId);
}
