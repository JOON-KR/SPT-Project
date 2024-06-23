package com.sptp.dawnary.like.repository;

import com.sptp.dawnary.like.domain.Like;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository <Like, Long> {

    List<Like> findByMemberId(Long memberId);

    Optional<Like> findByMemberIdAndSeriesId(Long memberId, Long seriesId);

}
