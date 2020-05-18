-- 회원

CREATE TABLE member(
    userNo NUMBER, --회원번호 (기본키)
    email VARCHAR2(50) NOT NULL, -- 이메일
    userId VARCHAR2(50) NOT NULL, -- 아이디
    userPwd VARCHAR2(200) NOT NULL, -- 비밀번호
    userName VARCHAR2(50) NOT NULL,  -- 이름
    nickname VARCHAR2(50), -- 별명 (OOO님 커피 나왔습니다~)
    created_date DATE DEFAULT SYSDATE, -- 가입일시
    updated_date DATE DEFAULT SYSDATE, -- 회원정보 수정일시
    phone VARCHAR2(50), -- 휴대폰번호
    enabled NUMBER(1) DEFAULT 1, -- 회원탈퇴여부 (0: 탈퇴, 1:회원(기본값))
    CONSTRAINT pk_member_num PRIMARY KEY(num)
);

CREATE SEQUENCE member_seq -- 회원번호 시퀀스 
    START WITH 1
    INCREMENT BY 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;

-- 공지사항

CREATE TABLE notice(
    num NUMBER, -- 게시글 번호(기본키)
    userNo NUMBER NOT NULL, -- 작성자 회원번호
    subject VARCHAR2(255) NOT NULL, -- 제목
    content VARCHAR2(4000) NOT NULL, -- 내용
    views NUMBER DEFAULT 0, -- 조회수
    created_date DATE DEFAULT SYSDATE, -- 게시글 작성일시
    updated_date DATE DEFAULT SYSDATE, -- 게시글 수정일시
    CONSTRAINT pk_notice_num PRIMARY KEY(num), 
    CONSTRAINT fk_notice_userNo FOREIGN KEY(userNo) REFERENCES member(userNo)
);

CREATE SEQUENCE notice_seq -- 게시글번호 시퀀스
    START WITH 1
    INCREMENT BY 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;

-- 이벤트

CREATE TABLE event(
    num NUMBER, -- 이벤트 게시글번호 (기본키)
    userNo NUMBER NOT NULL, -- 작성자 회원번호
    subject VARCHAR2(255) NOT NULL, -- 제목
    content VARCHAR2(4000) NOT NULL, -- 내용
    views NUMBER DEFAULT 0, -- 조회수
    start_date DATE NOT NULL, -- 이벤트 시작일자
    end_date DATE NOT NULL, -- 이벤트 종료일자
    created_date DATE DEFAULT SYSDATE, -- 게시글 작성일시
    updated_date DATE DEFAULT SYSDATE, -- 게시글 수정일시
    CONSTRAINT pk_notice_num PRIMARY KEY(num),
    CONSTRAINT fk_notice_userNo FOREIGN KEY(userNo) REFERENCES member(userNo)
);

CREATE SEQUENCE event_seq -- 이벤트 게시글 일련번호
    START WITH 1
    INCREMENT BY 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;

-------------
-- 메뉴 구분

CREATE TABLE menu_category(
    category NUMBER, -- 메뉴 카테고리 구분자 (기본키) (1: 커피, 2: 에이드, 3: 베이커리)
    categoryName VARCHAR2(255), -- 메뉴이름 (커피, 에이드, 베이커리)
    CONSTRAINT pk_menu_category PRIMARY KEY(category)
);

CREATE SEQUENCE menu_category_seq --메뉴 카테고리 구분자 일련번호
    START WITH 1
    INCREMENT BY 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;


CREATE TABLE menu( 
    num NUMBER, -- 메뉴 일련번호 (1: 아메리카노, 2: 크루아상 3: 카페모카 ...)
    category NUMBER NOT NULL, -- 메뉴 카테고리 구분자
    menuName VARCHAR2(255) NOT NULL, -- 메뉴이름 (아메리카노, 크루아상, 카페모카 ...)
    text VARCHAR2(4000) NOT NULL, -- 메뉴 소개글
    price NUMBER DEFAULT 0, -- 가격 (구매 시 필요)
    CONSTRAINT pk_menu_num PRIMARY KEY(num),
    CONSTRAINT fk_menu_category FOREIGN KEY(category) REFERENCES menu_category(category)
);

CREATE SEQUENCE menu_seq -- 메뉴 일련번호 시퀀스
    START WITH 1
    INCREMENT BY 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;


----------------
-- 매장 정보

CREATE TABLE store(
    storeNum NUMBER, -- 매장일련번호
    storeName VARCHAR(200), --매장 이름
    tel VARCHAR(50),
    storeAddress VARCHAR(500), -- 매장 주소
    CONSTRAINT pk_store_num PRIMARY KEY storeNum
);

CREATE SEQUENCE store_seq -- 매장 일련번호 시퀀스
    START WITH 1
    INCREMENT BY 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;


----------------
--주문과 주문 상세

CREATE order_status(
    statusNum NUMBER, -- 1: 결제완료, 2: 제조대기, 3: 제조 중, 4: 제조 완료
    statusName VARCHAR(100),
    CONSTRAINT pk_status_num PRIMARY KEY(statusNum)
);

CREATE SEQUENCE order_status -- 주문 상태번호
    START WITH 1
    INCREMENT BY 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;


CREATE TABLE order(
    orderNum NUMBER,
    totalPaymentAmount NUMBER NOT NULL, --최종 결제금액
    storeNum NUMBER NOT NULL, --주문 매장번호
    statusNum NUMBER NOT NULL,
    cardNum NUMBER, -- ALTER TABLE로 제약사항 추가하기
    order_date DATE DEFAULT SYSDATE, --주문일시
    cancelNum NUMBER, -- ALTER TABLE로 제약사항 추가하기
    CONSTRAINT pk_order_num PRIMARY KEY(orderNum),
-- CONSTRAINT fk_cancel_num FOREIGN KEY(cancelNum) REFERENCES order_cancel(cancelNum)
-- CONSTRAINT fk_cards_num FOREIGN KEY(cardNum) REFERENCES cards(cardNum)
);

CREATE SEQUENCE order_seq -- 주문 일련번호 시퀀스
    START WITH 1
    INCREMENT BY 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;

CREATE TABLE order_cancel(
    cancelNum NUMBER,
    orderNum NUMBER,
    paymentAmount NUMBER,
    canceled_date DATE DEFAULT SYSDATE,
    CONSTRAINT pk_cancel_num PRIMARY KEY(cancelNum),
    CONSTRAINT fk_order_num FOREIGN KEY(orderNum) REFERENCES order(orderNum);
);

CREATE SEQUENCE order_cancel_seq -- 주문 일련번호 시퀀스
    START WITH 1
    INCREMENT BY 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;

-- 결제 버튼 누를 때 DB에 입력
-- 결제 이전에는 세션에 List<>를 만들어 두고 활용하는 것이 좋을 듯!
CREATE TABLE order_detail(
    detailNum NUMBER, --주문상세일련번호
    orderNum NUMBER,
    unitPrice NUMBER NOT NULL, -- 단가
    quantity NUMBER NOT NULL, -- 수량
    paymentAmount NUMBER NOT NULL, -- 결제금액 (할인 등 변수 고려)
    CONSTRAINT pk_order_detail_num PRIMARY KEY(detailNum),
    CONSTRAINT fk_order_num PRIMARY KEY(orderNum) REFERENCES order(orderNum)
);


-- 카드 보유내역
-- 카드 보유현황

CREATE TABLE card_model(
    modelNum NUMBER,
    modelName VARCHAR(100),
    thumbnail VARCHAR(255),
    CONSTRAINT pk_model_num PRIMARY KEY(modelNum)
);

CREATE TABLE cards(
    cardNum NUMBER,
    userNum NUMBER NOT NULL,
    modelNum NUMBER,
    cardIdentity VARCHAR(16), -- 16자리 하이픈없이 숫자만
    CONSTRAINT pk_cards_num PRIMARY KEY(cardNum),
    CONSTRAINT fk_model_num FOREIGN KEY(modelNum) REFERENCES card_model(modelNum)
);

---제약사항 추가
ALTER TABLE order ADD CONSTRAINT fk_cancel_num FOREIGN KEY(cancelNum) REFERENCES order_cancel(cancelNum);
ALTER TABLE order ADD CONSTRAINT fk_cards_num FOREIGN KEY(cardNum) REFERENCES cards(cardNum);

