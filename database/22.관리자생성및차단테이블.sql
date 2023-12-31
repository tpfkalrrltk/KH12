--관리자 생성
INSERT into member(MEMBER_ID,MEMBER_PW ,MEMBER_NICKNAME, MEMBER_LEVEL  )values('admin1234', 'Admin1234!','관리자1234','관리자');
commit;


--차단된 회원 테이블
CREATE table member_block(
member_id references member(member_id) on DELETE cascade,
block_time date default sysdate not null,
primary key(member_id)


create or replace view member_list as
select
	M.* , 
	nvl2(B.member_id, 'Y','N') block
from member M left outer join member_block B
	on M.member_id= B.member_id;
