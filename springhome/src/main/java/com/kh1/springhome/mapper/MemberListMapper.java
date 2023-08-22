package com.kh1.springhome.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;


import com.kh1.springhome.dto.MemberListDto;

@Component
public class MemberListMapper implements RowMapper<MemberListDto> {

	@Override
	public MemberListDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		MemberListDto memberListDto = new MemberListDto();
		memberListDto.setMemberAddr1(rs.getString("member_addr1"));
		memberListDto.setMemberAddr2(rs.getString("member_addr2"));
		memberListDto.setMemberBirth(rs.getString("member_birth"));
		memberListDto.setMemberChange(rs.getDate("member_change"));
		memberListDto.setMemberContact(rs.getString("member_contact"));
		memberListDto.setMemberEmail(rs.getString("member_email"));
		memberListDto.setMemberId(rs.getString("member_id"));
		memberListDto.setMemberJoin(rs.getDate("member_join"));
		memberListDto.setMemberLevel(rs.getString("member_level"));
		memberListDto.setMemberNickname(rs.getString("member_nickname"));
		memberListDto.setMemberPoint(rs.getInt("member_point"));
		memberListDto.setMemberPost(rs.getString("member_post"));
		memberListDto.setMemberPw(rs.getString("member_pw"));
		memberListDto.setMemberLogin(rs.getDate("member_login"));
		memberListDto.setBlock(rs.getString("block"));
		return memberListDto;
	}

}
