-- 회원

CREATE TABLE member(
    userNum NUMBER, --회원번호 (기본키)
    email VARCHAR2(50) NOT NULL, -- 이메일
    userId VARCHAR2(50) NOT NULL, -- 아이디
    userPwd VARCHAR2(200) NOT NULL, -- 비밀번호
    userName VARCHAR2(50) NOT NULL,  -- 이름
    nickname VARCHAR2(50), -- 별명 (OOO님 커피 나왔습니다~)
    created_date DATE DEFAULT SYSDATE, -- 가입일시
    updated_date DATE DEFAULT SYSDATE, -- 회원정보 수정일시
    phone VARCHAR2(50), -- 휴대폰번호
    enabled NUMBER(1) DEFAULT 1, -- 회원탈퇴여부 (0: 탈퇴, 1:회원(기본값))
    CONSTRAINT pk_member_userNum PRIMARY KEY(userNum)
    CONSTRAINT uk_member_userId UNIQUE(userId),
    CONSTRAINT uk_member_email UNIQUE(email),
    CONSTRAINT uk_member_phone UNIQUE(phone)
);

CREATE TABLE member_admin(
    userNum NUMBER,
    CONSTRAINT pk_admin_userNum PRIMARY KEY (adminNum),
    CONSTRAINT fk_admin_userNum FOREIGN KEY(adminNum) REFERENCES member(userNum)
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
    userNum NUMBER NOT NULL, -- 작성자 회원번호
    subject VARCHAR2(255) NOT NULL, -- 제목
    content VARCHAR2(4000) NOT NULL, -- 내용
    views NUMBER DEFAULT 0, -- 조회수
    created_date DATE DEFAULT SYSDATE, -- 게시글 작성일시
    updated_date DATE DEFAULT SYSDATE, -- 게시글 수정일시
    CONSTRAINT pk_notice_num PRIMARY KEY(num), 
    CONSTRAINT fk_notice_userNum FOREIGN KEY(userNum) REFERENCES member(userNum)
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
    userNum NUMBER NOT NULL, -- 작성자 회원번호
    subject VARCHAR2(255) NOT NULL, -- 제목
    content VARCHAR2(4000) NOT NULL, -- 내용
    views NUMBER DEFAULT 0, -- 조회수
    start_date DATE NOT NULL, -- 이벤트 시작일자
    end_date DATE NOT NULL, -- 이벤트 종료일자
    created_date DATE DEFAULT SYSDATE, -- 게시글 작성일시
    updated_date DATE DEFAULT SYSDATE, -- 게시글 수정일시
    CONSTRAINT pk_event_num PRIMARY KEY(num),
    CONSTRAINT fk_event_userNum FOREIGN KEY(userNum) REFERENCES member(userNum)
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
    categoryNum NUMBER, -- 메뉴 카테고리 구분자 (기본키) (1: 커피, 2: 에이드, 3: 베이커리)
    categoryName VARCHAR2(255), -- 메뉴이름 (커피, 에이드, 베이커리)
    CONSTRAINT pk_menu_category PRIMARY KEY(categoryNum)
);

CREATE SEQUENCE menu_category_seq --메뉴 카테고리 구분자 일련번호
    START WITH 1
    INCREMENT BY 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;


CREATE TABLE menu( 
    menuNum NUMBER, -- 메뉴 일련번호 (1: 아메리카노, 2: 크루아상 3: 카페모카 ...)
    categoryNum NUMBER NOT NULL, -- 메뉴 카테고리 구분자
    menuName VARCHAR2(255) NOT NULL, -- 메뉴이름 (아메리카노, 크루아상, 카페모카 ...)
    thumbnail VARCHAR2(500),
    text VARCHAR2(4000) NOT NULL, -- 메뉴 소개글
    price NUMBER DEFAULT 0, -- 가격 (구매 시 필요)
    CONSTRAINT pk_menu_num PRIMARY KEY(menuNum),
    CONSTRAINT fk_menu_category FOREIGN KEY(categoryNum) REFERENCES menu_category(categoryNum)
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
    storeName VARCHAR2(200), --매장 이름
    tel VARCHAR2(50),
    storeAddress VARCHAR2(500), -- 매장 주소
    visible NUMBER(1) DEFAULT 1, -- 0(목록에서 안 보임), 1(보임)
    CONSTRAINT pk_store_num PRIMARY KEY (storeNum)
);

CREATE SEQUENCE store_seq -- 매장 일련번호 시퀀스
    START WITH 1
    INCREMENT BY 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;


----------------
--주문과 주문 상세

CREATE TABLE order_status(
    statusNum NUMBER, -- 1: 결제완료, 2: 제조대기, 3: 제조 중, 4: 제조 완료
    statusName VARCHAR(100),
    CONSTRAINT pk_status_num PRIMARY KEY(statusNum)
);

CREATE SEQUENCE order_status_seq -- 주문 상태번호
    START WITH 1
    INCREMENT BY 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;


CREATE TABLE order_history(
    orderNum NUMBER,
    totalPaymentAmount NUMBER NOT NULL, --최종 결제금액
    storeNum NUMBER, --주문 매장번호
    statusNum NUMBER NOT NULL,
    userNum NUMBER NOT NULL,
    cardNum NUMBER NOT NULL, -- ALTER TABLE로 제약사항 추가하기
    order_date DATE DEFAULT SYSDATE, --주문일시
    cancelNum NUMBER, -- ALTER TABLE로 제약사항 추가하기
    CONSTRAINT pk_order_history_num PRIMARY KEY(orderNum),
    CONSTRAINT fk_order_history_userNum FOREIGN KEY(userNum) REFERENCES member(userNum),
    CONSTRAINT fk_order_history_statusNum FOREIGN KEY(statusNum) REFERENCES order_status(statusNum)
-- CONSTRAINT fk_cancel_num FOREIGN KEY(cancelNum) REFERENCES order_cancel(cancelNum)
-- CONSTRAINT fk_cards_num FOREIGN KEY(cardNum) REFERENCES cards(cardNum)
);

CREATE SEQUENCE order_history_seq -- 주문 일련번호 시퀀스
    START WITH 1
    INCREMENT BY 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;

CREATE TABLE order_cancel(
    cancelNum NUMBER,
    orderNum NUMBER NOT NULL,
    paymentAmount NUMBER,
    cardNum NUMBER NOT NULL,
    canceled_date DATE DEFAULT SYSDATE,
    CONSTRAINT pk_cancel_num PRIMARY KEY(cancelNum),
    CONSTRAINT fk_order_cancel_card_num FOREIGN KEY(cardNum) REFERENCES cards(cardNum),
    CONSTRAINT fk_order_cancel_num FOREIGN KEY(orderNum) REFERENCES order_history(orderNum)
);

CREATE SEQUENCE order_cancel_seq -- 주문 취소
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
    menuNum NUMBER NOT NULL,
    unitPrice NUMBER NOT NULL, -- 단가
    quantity NUMBER NOT NULL, -- 수량
    paymentAmount NUMBER NOT NULL, -- 결제금액 (할인 등 변수 고려)
    CONSTRAINT pk_order_detail_num PRIMARY KEY(detailNum),
    CONSTRAINT fk_order_detail_menu_num FOREIGN KEY(menuNum) REFERENCES menu(menuNum),
    CONSTRAINT fk_order_detail_num FOREIGN KEY(orderNum) REFERENCES order_history(orderNum)
);

CREATE SEQUENCE order_detail_seq -- 주문 일련번호 시퀀스
    START WITH 1
    INCREMENT BY 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;

-- 카드 보유내역
-- 카드 보유현황

CREATE TABLE card_model(
    modelNum NUMBER,
    modelName VARCHAR2(100) NOT NULL,
    text VARCHAR2(4000) NOT NULL ,
    thumbnail VARCHAR2(500),
    CONSTRAINT pk_model_num PRIMARY KEY(modelNum)
);

CREATE SEQUENCE card_model_seq -- 카드 모델(종류) 시퀀스
    START WITH 1
    INCREMENT BY 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;


CREATE TABLE cards(
    cardNum NUMBER,
    cardName VARCHAR2(255),
    userNum NUMBER NOT NULL,
    modelNum NUMBER NOT NULL,
    cardIdentity VARCHAR2(16), -- 16자리 하이픈없이 숫자만
    balance NUMBER DEFAULT 0,
    isClosed NUMBER(1) DEFAULT 0, --0:열림, 1:닫힘
    CONSTRAINT pk_cards_num PRIMARY KEY(cardNum),
    CONSTRAINT uk_cards_identity UNIQUE(cardIdentity),
    CONSTRAINT fk_model_num FOREIGN KEY(modelNum) REFERENCES card_model(modelNum)
);

CREATE SEQUENCE cards_seq -- 카드 일련번호 시퀀스
    START WITH 1
    INCREMENT BY 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;

CREATE TABLE card_charge(--카드 충전내역
    chargeNum NUMBER,
    cardNum NUMBER NOT NULL,
    chargeAmount NUMBER NOT NULL,
    charge_date DATE DEFAULT SYSDATE,
    CONSTRAINT pk_card_charge_num PRIMARY KEY(chargeNum),
    CONSTRAINT fk_card_charge_cardNum FOREIGN KEY(cardNum) REFERENCES cards(cardNum)
);

CREATE SEQUENCE card_charge_seq -- 카드충전내역 시퀀스
    START WITH 1
    INCREMENT BY 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;




---제약사항 추가
ALTER TABLE order_history ADD CONSTRAINT fk_cancel_num FOREIGN KEY(cancelNum) REFERENCES order_cancel(cancelNum);
ALTER TABLE order_history ADD CONSTRAINT fk_cards_num FOREIGN KEY(cardNum) REFERENCES cards(cardNum);



--테이블 삭제
ALTER TABLE order_history DROP CONSTRAINT fk_cancel_num;
ALTER TABLE order_history DROP CONSTRAINT fk_cards_num;
DROP TABLE NOTICE PURGE;
DROP SEQUENCE NOTICE_SEQ;
DROP TABLE EVENT PURGE;
DROP SEQUENCE EVENT_SEQ;
DROP TABLE MENU PURGE;
DROP SEQUENCE MENU_SEQ;
DROP TABLE MENU_CATEGORY PURGE;
DROP SEQUENCE MENU_CATEGORY_SEQ;
DROP TABLE STORE PURGE;
DROP SEQUENCE  STORE_SEQ;
DROP TABLE ORDER_STATUS PURGE;
DROP SEQUENCE  ORDER_STATUS_SEQ;
DROP TABLE ORDER_CANCEL PURGE;
DROP SEQUENCE ORDER_CANCEL_SEQ;
DROP TABLE ORDER_DETAIL PURGE;
DROP SEQUENCE ORDER_DETAIL_SEQ;
DROP TABLE ORDER_HISTORY PURGE;
DROP SEQUENCE ORDER_HISTORY_SEQ;
DROP TABLE CARDS PURGE;
DROP SEQUENCE CARDS_SEQ;
DROP TABLE CARD_MODEL PURGE;
DROP SEQUENCE CARD_MODEL_SEQ;
DROP TABLE MEMBER PURGE;
DROP SEQUENCE MEMBER_SEQ;


SELECT * FROM TAB;
SELECT * FROM SEQ;


-- 카드모델 샘플 데이터

INSERT INTO card_model(modelNum, modelName, text, thumbnail) VALUES(card_model_seq.NEXTVAL, '열정응원카드', '당신의 열정을 응원합니다', '/resource/images/members/card/card01.png');
INSERT INTO card_model(modelNum, modelName, text, thumbnail) VALUES(card_model_seq.NEXTVAL, '쿠앤크레몬카드', '레몬처럼 상큼한 쿠앤크카드', '/resource/images/members/card/card02.png');
INSERT INTO card_model(modelNum, modelName, text, thumbnail) VALUES(card_model_seq.NEXTVAL, '마음에 싹트다 카드', '쓸수록 마음이 싹트는 카드', '/resource/images/members/card/card03.png');
INSERT INTO card_model(modelNum, modelName, text, thumbnail) VALUES(card_model_seq.NEXTVAL, '오렌지 카드', '오렌지가 가득한 새콤한 쿠앤크카드', '/resource/images/members/card/card04.png');
INSERT INTO card_model(modelNum, modelName, text, thumbnail) VALUES(card_model_seq.NEXTVAL, '레드 카드', '레드레드한 쿠앤크카드', '/resource/images/members/card/card05.png');
INSERT INTO card_model(modelNum, modelName, text, thumbnail) VALUES(card_model_seq.NEXTVAL, '스튜던트 카드', '초심으로 돌아가 연필을 잡자 카드', '/resource/images/members/card/card06.png');
INSERT INTO card_model(modelNum, modelName, text, thumbnail) VALUES(card_model_seq.NEXTVAL, '나는야 백조카드', '핑크핑크한 백조를 보셨나요?', '/resource/images/members/card/card07.png');
INSERT INTO card_model(modelNum, modelName, text, thumbnail) VALUES(card_model_seq.NEXTVAL, '네추럴 카드', '로이더 아니고 네추럴 카드', '/resource/images/members/card/card08.png');

INSERT INTO card_model(modelNum, modelName, text, thumbnail) VALUES(card_model_seq.NEXTVAL, '우주를 줄게카드', '우주의 별만큼 원두가 한가득', '/resource/images/members/card/card09.png');
INSERT INTO card_model(modelNum, modelName, text, thumbnail) VALUES(card_model_seq.NEXTVAL, '빛망울 카드', '아름다운 당신의 미래를 응원하는 카드', '/resource/images/members/card/card10.png');
INSERT INTO card_model(modelNum, modelName, text, thumbnail) VALUES(card_model_seq.NEXTVAL, '사랑이 넘치는 카드', '사먹는 만큼 늘어나는 풋풋한 사랑카드', '/resource/images/members/card/card11.png');
INSERT INTO card_model(modelNum, modelName, text, thumbnail) VALUES(card_model_seq.NEXTVAL, '바다에 퐁당 카드', '무더운 여름 시원한 카드를 만들면 행복해져요', '/resource/images/members/card/card12.png');
INSERT INTO card_model(modelNum, modelName, text, thumbnail) VALUES(card_model_seq.NEXTVAL, '아몰라 카드', '카드이름 뭐라고 지을지 몰라서 막 만든 카드', '/resource/images/members/card/card13.png');
INSERT INTO card_model(modelNum, modelName, text, thumbnail) VALUES(card_model_seq.NEXTVAL, '엄브렐라 카드', '너의 우산이 되어줄게', '/resource/images/members/card/card14.png');
INSERT INTO card_model(modelNum, modelName, text, thumbnail) VALUES(card_model_seq.NEXTVAL, '고급진 카드', '명품 브랜드 숍에서나 볼 수 있을 법한 명품 카드입니다.', '/resource/images/members/card/card15.png');
INSERT INTO card_model(modelNum, modelName, text, thumbnail) VALUES(card_model_seq.NEXTVAL, '커피좋아 카드', '나는 커피가 너무 좋아 카드', '/resource/images/members/card/card16.png');

INSERT INTO card_model(modelNum, modelName, text, thumbnail) VALUES(card_model_seq.NEXTVAL, '시티가좋아 카드', '평생 도시에서만 살고 싶은 카드', '/resource/images/members/card/card17.png');
INSERT INTO card_model(modelNum, modelName, text, thumbnail) VALUES(card_model_seq.NEXTVAL, '책벌레 카드', '책을 읽을 땐 항상 커피가 옆에 있으면 좋겠어', '/resource/images/members/card/card18.png');
INSERT INTO card_model(modelNum, modelName, text, thumbnail) VALUES(card_model_seq.NEXTVAL, '소망을 담아 카드', '소망을 담아 하늘로 훨훨 날려 버려', '/resource/images/members/card/card19.png');
INSERT INTO card_model(modelNum, modelName, text, thumbnail) VALUES(card_model_seq.NEXTVAL, '신발끈 카드', '더는 신발끈이 풀리지 않아', '/resource/images/members/card/card20.png');
INSERT INTO card_model(modelNum, modelName, text, thumbnail) VALUES(card_model_seq.NEXTVAL, '생일축하 카드', '마음을 담아 생일을 축하해 보세요!', '/resource/images/members/card/card21.png');
INSERT INTO card_model(modelNum, modelName, text, thumbnail) VALUES(card_model_seq.NEXTVAL, '도넛만 먹고파 카드', '알록달록한 도넛을 보면 배가 고파', '/resource/images/members/card/card22.png');
INSERT INTO card_model(modelNum, modelName, text, thumbnail) VALUES(card_model_seq.NEXTVAL, '탄수화물 중독 카드', '탄수화물 섭취 뒤엔 커피를 마셔야 꿀맛', '/resource/images/members/card/card23.png');
INSERT INTO card_model(modelNum, modelName, text, thumbnail) VALUES(card_model_seq.NEXTVAL, '젤리빈 카드', '참 색이 영롱하니 먹기에도 좋아 보인다.', '/resource/images/members/card/card24.png');
INSERT INTO card_model(modelNum, modelName, text, thumbnail) VALUES(card_model_seq.NEXTVAL, '개발자 카드', '개발자라면 1클래스 1커피', '/resource/images/members/card/card25.png');
INSERT INTO card_model(modelNum, modelName, text, thumbnail) VALUES(card_model_seq.NEXTVAL, '하늘을 담아 카드', '저 하늘의 별을 따다 줘 해가 뜨기 전에', '/resource/images/members/card/card26.png');
INSERT INTO card_model(modelNum, modelName, text, thumbnail) VALUES(card_model_seq.NEXTVAL, '용자 카드', '산 정상에 올라간 사람만 구매하세요', '/resource/images/members/card/card27.png');
INSERT INTO card_model(modelNum, modelName, text, thumbnail) VALUES(card_model_seq.NEXTVAL, '나무를 부탁해 카드', '환경보호를 위해 힘쓰는 사람만 구매할 수 있습니다', '/resource/images/members/card/card28.png');
INSERT INTO card_model(modelNum, modelName, text, thumbnail) VALUES(card_model_seq.NEXTVAL, '지구를 지켜라 카드', '코로나로부터 지구를 구해주세요', '/resource/images/members/card/card29.png');
INSERT INTO card_model(modelNum, modelName, text, thumbnail) VALUES(card_model_seq.NEXTVAL, '새싹틔움 카드', '1년 365일이 식목일인 카드', '/resource/images/members/card/card30.png');
INSERT INTO card_model(modelNum, modelName, text, thumbnail) VALUES(card_model_seq.NEXTVAL, '심플 카드', '디자인의 심플함을 추구한다면 이 카드를 등록하세요.', '/resource/images/members/card/card-default.png');

COMMIT;

-- 메뉴 카테고리 샘플

INSERT INTO MENU_CATEGORY(categoryNum, categoryName) VALUES(menu_category_seq.NEXTVAL, '커피');
INSERT INTO MENU_CATEGORY(categoryNum, categoryName) VALUES(menu_category_seq.NEXTVAL, '에이드');
INSERT INTO MENU_CATEGORY(categoryNum, categoryName) VALUES(menu_category_seq.NEXTVAL, '베이커리');
INSERT INTO MENU_CATEGORY(categoryNum, categoryName) VALUES(menu_category_seq.NEXTVAL, '기타');
COMMIT;

-- 메뉴 샘플
INSERT INTO menu(menuNum, categoryNum, menuName, thumbnail, text, price) VALUES(menu_seq.NEXTVAL, 1, '아메리카노', 'TODO:썸네일주소', '맛있는 아메리카노', 3500);
COMMIT;

-- 지점

INSERT INTO store(storeNum, storeName, TEL, storeAddress, visible) VALUES(store_seq.NEXTVAL, '온라인', '1588-0000', '온라인 구매', 0);
INSERT INTO store(storeNum, storeName, TEL, storeAddress) VALUES(store_seq.NEXTVAL, '마포서교점', '1588-0000', '서울 마포구 서교동 10-1');
INSERT INTO store(storeNum, storeName, TEL, storeAddress) VALUES(store_seq.NEXTVAL, '남서울대점', '1588-0000', '충남 천안시 서북구 성환읍 성진로 999(성월리, 남경루)');
INSERT INTO store(storeNum, storeName, TEL, storeAddress) VALUES(store_seq.NEXTVAL, '동서울대점', '1588-0000', '경기 성남시 수정구 복정로 1010(복정동)');
INSERT INTO store(storeNum, storeName, TEL, storeAddress) VALUES(store_seq.NEXTVAL, '서울대중앙점', '1588-0000', '서울 관악구 남부순환로 999길 29(봉천동)');
INSERT INTO store(storeNum, storeName, TEL, storeAddress) VALUES(store_seq.NEXTVAL, '서울숲역점', '1588-0000', '서울 성동구 왕십리로 96-1');
INSERT INTO store(storeNum, storeName, TEL, storeAddress) VALUES(store_seq.NEXTVAL, '서울시립대점', '1588-0000', '서울 중구 세종대로 20길 23');
INSERT INTO store(storeNum, storeName, TEL, storeAddress) VALUES(store_seq.NEXTVAL, '서울신대점', '1588-0000', '경기 부천시 호현로 489번길 39(소사본동)');
INSERT INTO store(storeNum, storeName, TEL, storeAddress) VALUES(store_seq.NEXTVAL, '서울여대점', '1588-0000', '서울 노원구 노원로 333 (공릉동)');
INSERT INTO store(storeNum, storeName, TEL, storeAddress) VALUES(store_seq.NEXTVAL, '안산서울예대점', '1588-0000', '경기 안산시 단원구 예술대학로 111 (고잔동)');
INSERT INTO store(storeNum, storeName, TEL, storeAddress) VALUES(store_seq.NEXTVAL, '마포경찰서점', '1588-0000', '서울 마포구 마포대로 177 (공덕동)');
INSERT INTO store(storeNum, storeName, TEL, storeAddress) VALUES(store_seq.NEXTVAL, '마포공덕역점', '1588-0000', '서울 마포구 새창로 999 (도화동)');
INSERT INTO store(storeNum, storeName, TEL, storeAddress) VALUES(store_seq.NEXTVAL, '마포구청점', '1588-0000', '서울 마포구 월드컵로 995 (성산동) ');
INSERT INTO store(storeNum, storeName, TEL, storeAddress) VALUES(store_seq.NEXTVAL, '마포데시앙점', '1588-0000', '서울 마포구 독막로 222 (도화동)');
INSERT INTO store(storeNum, storeName, TEL, storeAddress) VALUES(store_seq.NEXTVAL, '마포점', '1588-0000', '서울 마포구 도화길 111(도화동)');
INSERT INTO store(storeNum, storeName, TEL, storeAddress) VALUES(store_seq.NEXTVAL, '을지로3가점', '1588-0000', '서울 중구 수표로 494 (저동22가)');
INSERT INTO store(storeNum, storeName, TEL, storeAddress) VALUES(store_seq.NEXTVAL, '강남YMCA점', '1588-0000', '서울 강남구 논현동');
COMMIT;


INSERT INTO order_status(statusNum, statusName) VALUES(order_status_seq.NEXTVAL, '결제 완료');
INSERT INTO order_status(statusNum, statusName) VALUES(order_status_seq.NEXTVAL, '제조 대기');
INSERT INTO order_status(statusNum, statusName) VALUES(order_status_seq.NEXTVAL, '제조 중');
INSERT INTO order_status(statusNum, statusName) VALUES(order_status_seq.NEXTVAL, '제조 완료');
INSERT INTO order_status(statusNum, statusName) VALUES(-1, '결제 취소');


COMMIT;

--카드 충전 포인트 샘플 데이터
INSERT INTO card_charge(chargeNum, cardNum, chargeAmount) VALUES(card_charge_seq.NEXTVAL, 1, 10000);
COMMIT;
----카드 충전 포인트 트리거

--포인트 충전 내역 등록/수정/삭제 트리거

--포인트 충전 시
CREATE OR REPLACE TRIGGER ins_card_charge_point
AFTER INSERT ON card_charge
FOR EACH ROW

BEGIN
     UPDATE cards SET balance = balance + :NEW.chargeAmount
           WHERE cardNum = :NEW.cardNum;
END;
/

-- 포인트 충전내역 수정 시 (기능 중 포인트 충전내역을 수정할 일은 없게 할 것이지만 혹시 몰라서 삽입)
CREATE OR REPLACE TRIGGER update_card_charge_point
AFTER UPDATE ON card_charge
FOR EACH ROW
BEGIN
     UPDATE cards SET balance = balance - :OLD.chargeAmount + :NEW.chargeAmount
            WHERE cardNum = :NEW.cardNum;
END;
/

-- 포인트 충전내역 삭제 시
CREATE OR REPLACE TRIGGER delete_card_charge_point
AFTER DELETE ON card_charge
FOR EACH ROW
BEGIN
     UPDATE cards SET balance = balance - :OLD.chargeAmount;
           WHERE cardNum = :OLD.cardNum;
END;
/

---트리거
-- 구매내역 입력 시
CREATE OR REPLACE TRIGGER ins_order_history_point
AFTER INSERT ON order_history
FOR EACH ROW

BEGIN
     UPDATE cards SET balance = balance - :NEW.totalPaymentAmount
           WHERE cardNum = :NEW.cardNum;
END;
/

-- 구매내역 수정 시
CREATE OR REPLACE TRIGGER update_order_history_point
AFTER UPDATE ON order_history
FOR EACH ROW
BEGIN
     UPDATE cards SET balance = balance + :OLD.totalPaymentAmount - :NEW.totalPaymentAmount
            WHERE cardNum = :NEW.cardNum;
END;
/

--구매내역 삭제 시
CREATE OR REPLACE TRIGGER delete_order_history_point
AFTER DELETE ON order_history
FOR EACH ROW
BEGIN
     UPDATE cards SET balance = balance + :OLD.totalPaymentAmount
           WHERE cardNum = :OLD.cardNum;
END;
/

--취소내역 트리거
-- 취소내역 입력 시
CREATE OR REPLACE TRIGGER insert_order_cancel
AFTER INSERT ON order_cancel
FOR EACH ROW

BEGIN
     UPDATE cards SET balance = balance + :NEW.paymentAmount
           WHERE cardNum = :NEW.cardNum;
END;
/

-- 취소내역 수정 시
CREATE OR REPLACE TRIGGER update_order_cancel
AFTER UPDATE ON order_cancel
FOR EACH ROW
BEGIN
     UPDATE cards SET balance = balance - :OLD.paymentAmount + :NEW.paymentAmount
            WHERE cardNum = :NEW.cardNum;
END;
/

--취소내역 삭제 시
CREATE OR REPLACE TRIGGER delete_order_cancel
AFTER DELETE ON order_cancel
FOR EACH ROW
BEGIN
     UPDATE cards SET balance = balance - :OLD.paymentAmount
           WHERE cardNum = :OLD.cardNum;
END;
/