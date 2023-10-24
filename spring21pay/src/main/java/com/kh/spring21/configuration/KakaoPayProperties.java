package com.kh.spring21.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "custom.kakopay")
public class KakaoPayProperties {
private String cid,key;
}
