package com.sptp.dawnary.global.util;

import org.springframework.security.core.context.SecurityContextHolder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class MemberInfo {

    private MemberInfo(){}

    public static Long getMemberId() {

        return Long.parseLong(SecurityContextHolder.getContext()
                .getAuthentication()
                .getName());
    }
}
