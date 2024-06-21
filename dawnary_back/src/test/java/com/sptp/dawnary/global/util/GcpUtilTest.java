package com.sptp.dawnary.global.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class GcpUtilTest {

    @InjectMocks
    private GcpUtil gcpUtil;

    @Mock
    private RestTemplate restTemplate;

    @Value("${GCP-API-KEY}")
    private String apiKey = "AIzaSyB6Ql-joPrSvQtUBUaM3tAtpAEijF8Ft3o"; // 테스트를 위한 기본 값 설정

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetSentiment() {
        // 테스트용 응답 생성
        String jsonResponse = "{ \"documentSentiment\": {\"magnitude\": 0.982, \"score\": 0.958}, \"languageCode\": \"ko\", \"sentences\": [ {\"text\": {\"content\": \"조아요조아요\", \"beginOffset\": 0}, \"sentiment\": {\"magnitude\": 0.983, \"score\": 0.959}}], \"languageSupported\": true }";
        ResponseEntity<String> responseEntity = ResponseEntity.ok(jsonResponse);

        // RestTemplate 모킹
        when(restTemplate.exchange(
                eq("https://language.googleapis.com/v2/documents:analyzeSentiment?key=" + apiKey),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(responseEntity);

        // 테스트 실행
        gcpUtil.getSentiment("조아요조아요");

    }
}
