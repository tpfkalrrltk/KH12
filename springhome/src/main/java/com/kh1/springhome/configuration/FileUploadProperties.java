package com.kh1.springhome.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
//application.properties에 있는 cutom.fileupload 설정을 저장할 파일
@Data
@Component
@ConfigurationProperties(prefix="custom.fileupload")
public class FileUploadProperties {
	private String home;
}
