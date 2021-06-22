-- 2021 06 22

/* 회원 */
DROP TABLE Member 
	CASCADE CONSTRAINTS;

/* 회원 */
CREATE TABLE Member (
	MemberCode NUMBER(4) NOT NULL CONSTRAINT MEMBER_CODE_PK PRIMARY KEY, /* 회원코드 */
	ID VARCHAR(15) NOT NULL, /* 회원아이디 */
	Password VARCHAR(20) NOT NULL, /* 비밀번호 */
	MName VARCHAR(50) NOT NULL, /* 이름 */
	Age INTEGER NOT NULL, /* 나이 */
	PhoneNum VARCHAR(30) NOT NULL, /* 전화번호 */
	Email VARCHAR(40) DEFAULT 'non@non.com', /* 이메일 */
	Point INTEGER DEFAULT 0 /* 포인트 */
);

/*설명추가*/
COMMENT ON TABLE Member IS '회원';
COMMENT ON COLUMN Member.MemberCode IS '회원코드';
COMMENT ON COLUMN Member.ID IS '회원아이디';
COMMENT ON COLUMN Member.Password IS '비밀번호';
COMMENT ON COLUMN Member.MName IS '이름';
COMMENT ON COLUMN Member.Age IS '나이';
COMMENT ON COLUMN Member.PhoneNum IS '전화번호';
COMMENT ON COLUMN Member.Email IS '이메일';
COMMENT ON COLUMN Member.Point IS '포인트';

CREATE SEQUENCE MEMBER_CODE_PK
START WITH 1
INCREMENT BY 1;