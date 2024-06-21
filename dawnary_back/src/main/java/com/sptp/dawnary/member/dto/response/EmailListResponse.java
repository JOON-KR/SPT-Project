package com.sptp.dawnary.member.dto.response;

import java.util.List;

import lombok.Builder;

@Builder
public record EmailListResponse(List<String> emails) {
}
