package com.sptp.dawnary.follow.dto.response;

import java.util.List;

import lombok.Builder;

@Builder
public record FollowMemberListResponse(List<FollowMemberResponse> followMemberList) {
}
