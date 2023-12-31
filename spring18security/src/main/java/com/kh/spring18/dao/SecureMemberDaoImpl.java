package com.kh.spring18.dao;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.kh.spring18.dto.SecureMemberDto;

@Repository
public class SecureMemberDaoImpl implements SecureMemberDao {

	@Autowired
	private SqlSession sqlsession;

	@Autowired
	BCryptPasswordEncoder encoder;

	@Override
	public void insert(SecureMemberDto dto) {
		// dto에 들어있는 비밀번호를 암호화 처리 한뒤 등록
		String origin = dto.getMemberPw();

		String encrypt = encoder.encode(origin);
		dto.setMemberPw(encrypt);

		sqlsession.insert("secureMember.join", dto);

	}

	@Override
	public SecureMemberDto selectOne(String memberId) {
		SecureMemberDto dto = sqlsession.selectOne("secureMember.find", memberId);
		return dto;
	}

	@Override
	public SecureMemberDto login(SecureMemberDto dto) {
		SecureMemberDto target = sqlsession.selectOne("secureMember.find", dto.getMemberId());
		if (target != null) {// 아이디가 존재하면
			boolean result = encoder.matches(dto.getMemberPw(), target.getMemberPw());
			if (result == true) {// 비밀번호가 암호화 도구에 의해 맞다고 판정되면
				return target;
			}
		}
		return null;
	}
}
