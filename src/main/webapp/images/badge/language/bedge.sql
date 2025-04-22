drop table tblBadge;

create table tblBadge(
    badgeSeq number PRIMARY key,
    badgeName varchar2(100),
    badgeDesc varchar2(500),
    badgeIcon varchar2(500),
    badgeCondition varchar2(1000),
    isActive char(1)
);

select * from tblBadge;


insert into tblbadge values (?, ?, ?, ?, ?, ?);

-- 언어 스택 완료 뱃지
insert into tblbadge values (1, 'Java', 'Java를 배웠습니다.', '자바이미지?', 'Java과정 모든 미션 완료', '0');
insert into tblbadge values (2, 'Python', 'Python을 배웠습니다.', '파이썬이미지?', 'Python과정 모든 미션 완료', '0');
insert into tblbadge values (3, 'C', 'C를 배웠습니다.', 'C이미지?', 'C과정 모든 미션 완료', '0');
insert into tblbadge values (4, 'C#', 'C#을 배웠습니다.', 'C#이미지?', 'C#과정 모든 미션 완료', '0');
insert into tblbadge values (5, 'C++', 'C++를 배웠습니다.', 'C++이미지?', 'C++과정 모든 미션 완료', '0');
insert into tblbadge values (6, 'C++', 'C++를 배웠습니다.', 'C++이미지?', 'C++과정 모든 미션 완료', '0');
insert into tblbadge values (7, 'PHP', 'PHP를 배웠습니다.', 'PHP이미지?', 'PHP과정 모든 미션 완료', '0');
insert into tblbadge values (8, 'MySQL', 'MySQL을 배웠습니다.', 'MySQL이미지?', 'MySQL과정 모든 미션 완료', '0');

-- 달성 뱃지
insert into tblbadge values (9, '씨앗', '미션을 1개이상풀었습니다.', '씨앗 이미지', '미션 1개 이상 제출 완료', '0');
insert into tblbadge values (10, '샛싹', '미션을 20개 이상 풀었습니다.', '샛싹이미지?', '미션 20개 이상 제출 완료', '0');
insert into tblbadge values (11, '나무', '미션을 100개 이상 풀었습니다.', '나무이미지?', '미션 100개 이상 제출 완료', '0');
insert into tblbadge values (12, '복습왕', '복습 문제를 20개 이상 풀었습니다.', '책이미지?', '복습 문제 20개 이상 완료', '0');
insert into tblbadge values (13, '출석왕', '20일 이상 연속 출석.', '달력이미지?', '20일 이상 연속 출석 완료', '0');

insert into tblbadge values (14, '아이언', '랭킹 1000등 이하.', '아이언 랭킹이미지?', '랭킹 1000등 이하', '0');
insert into tblbadge values (15, '브론즈', '랭킹 999등 이상.', '브론즈 랭킹이미지?', '랭킹 900~999등', '0');
insert into tblbadge values (16, '실버', '랭킹 899등 이상.', '실버 이미지?', '랭킹800~899등', '0');
insert into tblbadge values (17, '골드', '랭킹 700~799등.', '골드 이미지?', '랭킹700~799등', '0');
insert into tblbadge values (18, '플래티넘', '랭킹 500~699등.', '플래티넘 이미지?', '랭킹500~699등', '0');
insert into tblbadge values (19, '다이아', '랭킹 300~499등.', '다이아 이미지?', '랭킹300~499등', '0');
insert into tblbadge values (20, '마스터', '랭킹 100~299등.', '마스터 이미지?', '랭킹100~299등', '0');
insert into tblbadge values (21, '그랜드마스터', '랭킹 30~99등.', '그랜드 이미지?', '랭킹30~99등', '0');
insert into tblbadge values (22, '챌린저', '랭킹 1~10등.', '그랜드 이미지?', '랭킹1~10등', '0');

insert into tblbadge values (23, '1등', '랭킹 1등.', '1등 트로피 이미지?', '랭킹1등', '0');
insert into tblbadge values (24, '2등', '랭킹 2등.', '2등 트로피 이미지?', '랭킹2등', '0');
insert into tblbadge values (25, '3등', '랭킹 3등.', '3등 트로피 이미지?', '랭킹3등', '0');






