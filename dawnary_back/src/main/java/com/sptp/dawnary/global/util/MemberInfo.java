package com.sptp.dawnary.global.util;

import com.sptp.dawnary.member.domain.Member;
import com.sptp.dawnary.member.repository.MemberRepository;
import com.sptp.dawnary.security.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public final class MemberInfo {

    public static Long getMemberId() {

        return Long.parseLong(SecurityContextHolder.getContext()
                .getAuthentication()
                .getName());
    }
}
