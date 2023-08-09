package com.kh1.springhome.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kh1.springhome.dto.MemberDto;
import com.kh1.springhome.mapper.MemberMapper;

@Repository
public class MemberDaoImpl implements MemberDao {
	@Autowired
	private JdbcTemplate tem;

	@Autowired
	private MemberMapper memberMapper;

	@Override
	public void insert(MemberDto memberDto) {
		String sql = "insert into member(" + "member_id, member_pw, member_nickname, "
				+ "member_email, member_contact, member_birth, " + "member_post, member_addr1, member_addr2" + ") "
				+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		Object[] data = { memberDto.getMemberId(), memberDto.getMemberPw(), memberDto.getMemberNickname(),
				memberDto.getMemberEmail(), memberDto.getMemberContact(), memberDto.getMemberBirth(),
				memberDto.getMemberPost(), memberDto.getMemberAddr1(), memberDto.getMemberAddr2() };
		tem.update(sql, data);
	}

	@Override
	public MemberDto selectOne(String memberId) {
		String sql = "select * from member where member_id = ?";
		Object[] data = { memberId };
		List<MemberDto> list = tem.query(sql, memberMapper, data);
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public boolean updateMemberLogin(String memberId) {
		String sql = "update member set member_login=sysdate " + "where member_id = ?";
		Object[] data = { memberId };
		return tem.update(sql, data) > 0;

	}

	@Override
	public boolean updateMemberPw(String memberId, String changePw) {
		String sql = "update member " + "set member_pw=?, member_change=sysdate " + "where member_id = ?";
		Object[] data = { changePw, memberId };
		return tem.update(sql, data) > 0;
	}


	@Override
	public boolean updateMemberInfo(MemberDto memberDto) {
		String sql = "update member set member_nickname = ?, member_email= ?, "
				+ "member_contact =?, member_birth = ?, " + "member_post = ?, member_addr1 = ?, "
				+ "member_addr2 = ? where member_id = ? ";
		Object[] ob = { memberDto.getMemberNickname(), memberDto.getMemberEmail(), memberDto.getMemberContact(),
				memberDto.getMemberBirth(), memberDto.getMemberPost(), memberDto.getMemberAddr1(),
				memberDto.getMemberAddr2(),memberDto.getMemberId()
		};
		 return tem.update(sql,ob) > 0;
	}

	
	


}
