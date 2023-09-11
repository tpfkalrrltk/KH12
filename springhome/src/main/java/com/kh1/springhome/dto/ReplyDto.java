package com.kh1.springhome.dto;

import java.sql.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReplyDto {
private int replyNo, replyOrigin;
private String replyWriter, replyContent;
private Date replyTime;
}
