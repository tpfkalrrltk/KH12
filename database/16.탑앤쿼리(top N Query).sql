--top N Query
--서브 쿼리를 사용하여 데이터를 원하는 구간만큼 끊어내는 구문
-- 오라클은 rownum이라는 값을 조회시 사용할 수 있다.
-- rownum은 조회 결과에 순서를 부여한다.

--상품을 번호순으로 3개만 조회(비추천 아무떄나 못씀)
SELECT *from PRODUCT  where no<=3  order by no asc;


-- 상품을 가격이 저렴한 순으로 3개 만 조회
SELECT *from PRODUCT  order by no asc;
SELECT rownum, product.* from PRODUCT  order by no asc;

-- 정렬을 먼저하도록 구조 개선(로우넘)
SELECT rownum,  TMP.* FROM (
SELECT *from PRODUCT  order by PRICE  asc)TMP where rownum <=3;

--문제점 : 중간부터는 조회가 안됨
SELECT rownum,  TMP.* FROM (
SELECT *from PRODUCT  order by PRICE  asc)TMP BETWEEN  3 and 5;
-- 해결책 : rownum을 모두 붙인 다음 조회하도록 서브 쿼리 추가
--문제점 : rownum은 SELECT 마다 생긴다.
SELECT * FROM (
SELECT rownum,  TMP.* FROM (
SELECT *from PRODUCT  order by PRICE  asc
)TMP
)where rownum BETWEEN  3 and 5;
-- 해결책 : 두번쨰 SELECT 에서 발생한 rownum에 별칭을 부여
SELECT * FROM (
SELECT rownum rn,  TMP.* FROM (
SELECT *from PRODUCT  order by PRICE  asc
)TMP
)where rn BETWEEN  3 and 5;
