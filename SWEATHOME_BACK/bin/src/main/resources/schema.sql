DROP DATABASE IF EXISTS ssafit_db;
CREATE DATABASE ssafit_db;
use ssafit_db;

CREATE TABLE Video (
   video_id INT AUTO_INCREMENT PRIMARY KEY,
   video_title VARCHAR(200),
   view_cnt INT default 1,
   like_cnt INT default 0,
   rate_avg Double default 0,
   part VARCHAR(50), -- 운동부위
   link VARCHAR(255)
);
CREATE TABLE Review (
   review_id INT AUTO_INCREMENT PRIMARY KEY,
   video_id int not null,
   rate double not null, -- 평점
   user_id VARCHAR(15) not null,
   user_name VARCHAR(15) not null,
   content  TEXT not null,
   write_time DATETIME default now(),
   FOREIGN KEY (video_id) REFERENCES VIDEO(video_id)
       ON DELETE CASCADE  -- VIDEO 테이블의 video_id가 삭제될 때 관련 댓글도 삭제
       ON UPDATE CASCADE  -- VIDEO 테이블의 video_id가 변경될 때 댓글의 video_id도 함께 변경
);

CREATE TABLE User (
	user_id VARCHAR(15) PRIMARY KEY,
    user_pw VARCHAR(15) not null,
    user_name VARCHAR(15) not null,
    user_rank int default 1, -- 관리자 등급:0 일반 유저:1
    user_email varchar(50) not null,
    coach int default 1, -- 1,2,3 mode
    gachacoupon int default 50
);

CREATE TABLE Videolike (
	video_id INT ,
	user_id VARCHAR(15)  not null,
	PRIMARY KEY (video_id,user_id),
    FOREIGN KEY (video_id) REFERENCES VIDEO(video_id)
       ON DELETE CASCADE  -- VIDEO 테이블의 video_id가 삭제될 때 관련 댓글도 삭제
       ON UPDATE CASCADE,  -- VIDEO 테이블의 video_id가 변경될 때 댓글의 video_id도 함께 변경
	FOREIGN KEY (user_id) REFERENCES User (user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Event (
	user_id VARCHAR(15)  not null,
    receive_time DATE DEFAULT (CURDATE()), -- 이벤트 혜택을 받은 시간 (연,월, 일)
    PRIMARY KEY (user_id,receive_time),
	FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE Sticker (
	user_id VARCHAR(15)  not null,
    sticker_no int not null, -- 스티커 받은 번호 
    PRIMARY KEY (user_id,sticker_no),
	FOREIGN KEY (user_id) REFERENCES User (user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Stickerdict (
    sticker_no  int PRIMARY KEY,-- 스티커 받은 번호 
    sticker_name VARCHAR(15) not null
);


CREATE TABLE Exercise (
    user_id VARCHAR(15) not null,
    exercise_date DATE DEFAULT (CURDATE()) ,-- 운동한 날짜 
    start_time TIME, 
	end_time TIME, 
    exercise_time int,
    exercise_kind VARCHAR(15),
    PRIMARY KEY (user_id, start_time),  -- 운동한 날짜와 유저 ID로 기본키 설정
    FOREIGN KEY (user_id) REFERENCES User (user_id) ON DELETE CASCADE ON UPDATE CASCADE
);


INSERT INTO User (user_id, user_pw, user_name, user_rank, user_email)
VALUES ('oyj5775', '1106','유진이', 0,'oyj5775@naver.com'), ('ssafy','1234','김싸피',1,'ssafy@ssafy.com');
INSERT INTO Video (video_title, part, link) VALUES ( 'NO 층간소음 올인원 운동 - 40분 유산소운동 홈트 - 관절에 무리없이 체지방 태우기','전신', 'https://www.youtube.com/watch?v=myNjmnvI6x0');
INSERT INTO Video (video_title, part, link) VALUES ( '출렁이는 팔뚝살🔥빨리 빼려면 1달만 이 루틴하세요. (팔뚝살빼는운동/팔뚝살 빨리 빼는법/팔뚝살 완전 제거 운동)','상체','https://www.youtube.com/watch?v=T-bVqdhqW2U');
INSERT INTO Video (video_title,view_cnt, part, link) VALUES ( '[ENG] 딱 20분🔥 하루 운동 끝장내는 전신 & 아랫배 & 타바타 올인원 루틴',200,'전신','https://www.youtube.com/watch?v=zruKSHfD7b4');
INSERT INTO Video (video_title,view_cnt, part, link) VALUES ( '전신 지방 불태우는 다이어트 운동🔥 30분 서서하는 논스탑 전신유산소 - 스쿼트X 런지X 점핑X',150,'전신','https://www.youtube.com/watch?v=-qcA-GqCVeI');
INSERT INTO Video (video_title,view_cnt, part, link) VALUES ( '효과보장! 일주일만에 전신 살빼기🔥40분 서서하는 전신 근력 유산소 - 뱃살, 팔뚝, 등살, 다리살 제거',100,'전신','https://www.youtube.com/watch?v=Ammb_7sv_KA');
INSERT INTO Video (video_title,view_cnt, part, link) VALUES ( '살이 미친듯이 빠지는 걷기 운동 30분🔥 전신살 빼는 저강도 유산소 - 점핑X 스쿼트X 런지X',30,'전신','https://www.youtube.com/watch?v=sMIatEvTACI');
INSERT INTO Video (video_title,view_cnt, part, link) VALUES ( 'NO 층간소음 - 고민없이 하나로 끝내는 전신운동 근력 유산소 - 운동 못한 날 죄책감 씻어줄 30분 홈트',50,'전신','https://www.youtube.com/watch?v=4kZHHPH6heY');
INSERT INTO Video (video_title,view_cnt, part, link) VALUES ( '[ENG] (근육통주의!!!!) 🔥올인원🔥 전신 근력운동 50분 홈트레이닝',10,'전신','https://www.youtube.com/watch?v=A5MzlPgNcJM');



INSERT INTO Review (video_id, rate, user_id, user_name, content) VALUES ( 1,5, 'oyj5775','유진이','이 운동으로 40kg 뺐어요');
INSERT INTO Review (video_id, rate, user_id,user_name,content) VALUES ( 1,2, 'ssafy','김싸피','이 운동 안 좋네요');

Insert Into videolike(user_id,video_id) values ('oyj5775',1), ('ssafy',1),('ssafy',2);
INSERT INTO Stickerdict(sticker_no, sticker_name)
VALUES (1, "강성구"), (2, "김광현"),  (3, "김도은"),  (4, "김범수"),  (5, "김선웅"),  (6, "김유석"),  (7, "김윤"), (8, "김일태"), (9, "박민정"), (10, "박상용"),
(11, "박성혁"),  (12, "변찬현"), (13, "양지웅"),(14, "오유진"),  (15, "이강현"), (16, "이동엽"), (17, "이보경"), (18, "이수완"), (19, "이승철"),(20, "이창현"),
(21, "정상영"),(22, "정해준"),(23, "조수연"),(24, "최다환"),(25, "최은혜"), (26, "최이서"),(27, "현경찬"),(28, "황예빈"),(29, "대상혁"),(30, "양띵균");


INSERT INTO Event (user_id) VALUES ('oyj5775');
-- select * from Event
select * from Video;
select * from Review;
select * from Exercise;
-- 구글 제미나이 ai가 작성해준 영상 추가 구문 -
INSERT INTO Video (video_title, view_cnt, part, link) VALUES
("최고의 전신 근력운동 BEST5", 10, "전신", "https://www.youtube.com/watch?v=Iaa8YNDRbhg"),
("하루 한 번! 꼭 해야하는 10분 기본 전신근력 운동 홈트 (층간소음‍♀️♀️)", 10, "전신", "https://www.youtube.com/watch?v=aKzE3NNFEi4"),
("요일별운동 금요일 하체집중 근력운동 15분 루틴!", 10, "하체", "https://www.youtube.com/watch?v=pDFuLG0xrsU"),
("하체 근력을 강화하는 가장 빠른 방법, 50세 이후 필수 시청!  [50대 하체 운동, 중년 하체 운동, 런지, 햄스트링 컬, 볼 벽 스쿼트]", 10, "하체", "https://www.youtube.com/watch?v=NTetHa7N1Mc"),
("[10분하체근력운동] 카운트송-비니쌤(의정부시체육회)", 10, "하체", "https://www.youtube.com/watch?v=wYg3W24w99A"),
("하루 한 번! 꼭 해야하는 10분 기본 하체근력 운동 홈트 (층간소음‍♀️♀️)", 10, "하체", "https://www.youtube.com/watch?v=C4_2puAkxfs"),
("하체 날, 딱 10분 밖에 없다면 - 스쿼트 10가지 동작 - 하체운동 홈트 루틴", 10, "하체", "https://www.youtube.com/watch?v=DWYDL-WxF1U"),
("NO 층간소음 - 20분 상체운동 루틴 + 스트레칭", 10, "상체", "https://www.youtube.com/watch?v=e1DHt9wfQOA"),
("등, 가슴, 팔, 어깨 탄탄하게 만들기 - 덤벨 상체 운동 홈트 루틴", 10, "상체", "https://www.youtube.com/watch?v=xoWKLNwNva0"),
("균형잡힌 탄탄한 상체와 멋진 자세를 위한 덤벨 상체 운동 홈트 22분", 10, "상체", "https://www.youtube.com/watch?v=84-tL-12-uY"),
("[ENG] 덤벨 상체 운동 루틴 - 팔, 가슴, 어깨, 등을 단련하는 10가지 동작", 10, "상체", "https://www.youtube.com/watch?v=y3_u9_z6y20"),
("상체 맨몸 운동 루틴 - 팔, 어깨, 가슴, 등을 鍛煉하는 10가지 동작", 10, "상체", "https://www.youtube.com/watch?v=yF_z-fT1q3E"),
("[ENG] 5분 스트레칭 루틴 - 운동 전후 필수 스트레칭", 10, "스트레칭", "https://www.youtube.com/watch?v=2vJ6yB8zZ6A"),
("전신 스트레칭 루틴 - 운동 전후 필수 스트레칭", 10, "스트레칭", "https://www.youtube.com/watch?v=z_008_2_18U"),
("유연성 향상! 10분 스트레칭 루틴", 10, "스트레칭", "https://www.youtube.com/watch?v=yQ-63zV0b5E"),
("아침 스트레칭 루틴 - 몸을 깨우고 유연성 향상", 10, "스트레칭", "https://www.youtube.com/watch?v=Y-g8_9_6z5Q"),
("자는 전 스트레칭 루틴 - 숙면을 위한 스트레칭", 10, "스트레칭", "https://www.youtube.com/watch?v=2i_u_1-yQ0w");

Insert into Exercise (user_id,exercise_date, start_time,end_time, exercise_time,exercise_kind ) value ("ssafy", "2024-05-13", "15:05","16:05","60","달리기"),
("ssafy", "2024-05-14", "14:25","16:05","100","달리기"),
("ssafy", "2024-05-15", "14:05","16:05","120","숨쉬기"),
("ssafy", "2024-05-16", "13:05","16:05","180","전신운동"),
("ssafy", "2024-05-17", "12:05","16:05","240","달리기");
