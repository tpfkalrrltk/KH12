package com.kh.spring20.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {
 private String memberId, memberPw,memberNickname
 ,memberAddr1,memberAddr2,memberLevel,memberBirth,memberEmail;
 private int memberContact,memberPost,memberPoint;
	private Date memberLogin, memberJoin,memberChange;
}
