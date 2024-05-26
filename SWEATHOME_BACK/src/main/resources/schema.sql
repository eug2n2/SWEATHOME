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
   user_id VARCHAR(30) not null,
   user_name VARCHAR(15) not null,
   content  TEXT not null,
   write_time DATETIME default now(),
   FOREIGN KEY (video_id) REFERENCES VIDEO(video_id)
       ON DELETE CASCADE  -- VIDEO 테이블의 video_id가 삭제될 때 관련 댓글도 삭제
       ON UPDATE CASCADE  -- VIDEO 테이블의 video_id가 변경될 때 댓글의 video_id도 함께 변경
);

CREATE TABLE User (
	user_id VARCHAR(30) PRIMARY KEY,
    user_pw VARCHAR(15) default "kakao",
    user_name VARCHAR(15) not null,
    user_rank int default 1, -- 관리자 등급:0 일반 유저:1
    user_email varchar(30) not null,
    gachacoupon int default 51
);

CREATE TABLE Videolike (
	video_id INT ,
	user_id VARCHAR(30)  not null,
	PRIMARY KEY (video_id,user_id),
    FOREIGN KEY (video_id) REFERENCES VIDEO(video_id)
       ON DELETE CASCADE  -- VIDEO 테이블의 video_id가 삭제될 때 관련 댓글도 삭제
       ON UPDATE CASCADE,  -- VIDEO 테이블의 video_id가 변경될 때 댓글의 video_id도 함께 변경
	FOREIGN KEY (user_id) REFERENCES User (user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Event (
	user_id VARCHAR(30)   not null,
    receive_time DATE DEFAULT (CURDATE()), -- 이벤트 혜택을 받은 시간 (연,월, 일)
    PRIMARY KEY (user_id,receive_time),
	FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Sticker (
	user_id VARCHAR(30)   not null,
    sticker_no int not null, -- 스티커 받은 번호 
    PRIMARY KEY (user_id,sticker_no),
	FOREIGN KEY (user_id) REFERENCES User (user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Stickerdict (
    sticker_no  int PRIMARY KEY,-- 스티커 받은 번호 
    sticker_name VARCHAR(15) not null
);

CREATE TABLE Exercise (
    user_id VARCHAR(30) not null,
    exercise_date DATE DEFAULT (CURDATE()) ,-- 운동한 날짜 
    start_time TIME, 
	end_time TIME, 
    exercise_time int,
    exercise_kind VARCHAR(15),
    PRIMARY KEY (user_id, exercise_date,start_time),  -- 운동한 날짜와 유저 ID로 기본키 설정
    FOREIGN KEY (user_id) REFERENCES User (user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Videoask (
	ask_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(30) NOT NULL,
    user_name VARCHAR(15) not null,
    ask_title VARCHAR(200),
    link VARCHAR(255),
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

Insert Into Sticker(user_id, sticker_no) values ("ssafy",1),("ssafy",3);
INSERT INTO Event (user_id) VALUES ('oyj5775');
-- select * from Event
-- select * from Video;
-- select * from Review;
-- select * from Exercise;
-- 구글 제미나이 ai가 작성해준 영상 추가 구문 -
INSERT INTO Video (video_title, view_cnt, part, link) VALUES
("최고의 전신 근력운동 BEST5", 10, "전신", "https://www.youtube.com/watch?v=Iaa8YNDRbhg"),
("요일별운동 금요일 하체집중 근력운동 15분 루틴!", 10, "하체", "https://www.youtube.com/watch?v=pDFuLG0xrsU"),
("하체 근력을 강화하는 가장 빠른 방법, 50세 이후 필수 시청!  [50대 하체 운동, 중년 하체 운동, 런지, 햄스트링 컬, 볼 벽 스쿼트]", 10, "하체", "https://www.youtube.com/watch?v=NTetHa7N1Mc"),
("[10분하체근력운동] 카운트송-비니쌤(의정부시체육회)", 10, "하체", "https://www.youtube.com/watch?v=wYg3W24w99A"),
("하체 날, 딱 10분 밖에 없다면 - 스쿼트 10가지 동작 - 하체운동 홈트 루틴", 10, "하체", "https://www.youtube.com/watch?v=DWYDL-WxF1U"),
("NO 층간소음 - 20분 상체운동 루틴 + 스트레칭", 10, "상체", "https://www.youtube.com/watch?v=e1DHt9wfQOA"),
("등, 가슴, 팔, 어깨 탄탄하게 만들기 - 덤벨 상체 운동 홈트 루틴", 10, "상체", "https://www.youtube.com/watch?v=xoWKLNwNva0"),
("균형잡힌 탄탄한 상체와 멋진 자세를 위한 덤벨 상체 운동 홈트 22분", 10, "상체", "https://www.youtube.com/watch?v=U7IH4wyVMBU"),
("덤벨 상체 운동 10분 루틴 - 팔, 가슴, 어깨, 등에 자극 대박이에요!! (Upper body workout with dumbbell)", 5, "상체", "https://www.youtube.com/watch?v=MknWfiAmqx8"),
("운동 전 전신스트레칭! (부상예방, 가동범위 및 운동효과 증가, 운동 전 필수운동)", 200, "스트레칭", "https://www.youtube.com/watch?v=ahbAnkN4KJ0"),
("운동 전 필수! 8분으로 빠르게 끝내는 초간단 전신 스트레칭ㅣ다노티비", 20, "스트레칭", "https://www.youtube.com/watch?v=U6nnxml9GRs"),
("하루 10분 유연성 늘리기 따라해보세요 | 뻣뻣한 몸이 유연해지는 스트레칭", 150, "스트레칭", "https://www.youtube.com/watch?v=1lPY8TkZzsk"),
("밤새 굳은몸을 풀어주는🌞 매일아침, 눈뜨자마자 따라하는 10분 스트레칭 (morning stretch)", 101, "스트레칭", "https://www.youtube.com/watch?v=PMfN-xMKyaI"),
("자기전 침대에서 하는 요가 | 숙면을 위한 침대 스트레칭, 자기전 스트레칭, 숙면요가", 3010, "스트레칭", "https://youtube.com/watch?v=z53kSOPMMsQ"),
("효과보장!! 무조건 뱃살 빠지는 서서하는 복부운동 15분 - 15MIN STANDING ABS WORKOUT", 2010, "하체","https://www.youtube.com/watch?v=RM2vStU7HY4"),
('5분! 팔뚝살 빨리빼는운동 / 상체 다이어트 [팔뚝살 마라맛🔥]', 111, "상체" , "https://www.youtube.com/watch?v=MEzTdtFdfbI"),
("앞벅지 볼록, 뒷벅지 셀룰라이트, 허벅지 안쪽살 모조리 불태우고🔥 [여리탄탄 일자 허벅지] 되는 7일 루틴", 456 ,"하체","https://www.youtube.com/watch?v=dpBYYEhdofI"),
("얼굴살, 얼굴 붓기 빠지는 7분 루틴✨작은 얼굴, 브이라인 만들기 / 이중턱 없애고 탄력 업!",3, "스트레칭", "https://www.youtube.com/watch?v=ZFEj0cWxw08"),
( "이중턱, 투턱, 턱살 제거 챌린지✨ - 8분 루틴 | 날렵한 턱선, V라인 만들기" ,45, "스트레칭","https://www.youtube.com/watch?v=9wmASBCZppY"),
("운동 손가락 | 손가락을 늘리고 슬림하게 만드는 방법 | 아름다운 손을 위해", 4610 , "스트레칭" ,"https://www.youtube.com/watch?v=dyz21OyFbVM"),
("곧은 긴 다리를 얻으세요! O/고정다리 스트레칭💛" , 232,"하체" ,"https://www.youtube.com/watch?v=AKzHIU6Ha3I"),
("키가 크고 날씬해지는 방법| 자세교정스트레칭❤", 32 ,"스트레칭", "https://www.youtube.com/watch?v=Qhyw7Y-Juo8"),
("【K-POP 아이돌도 한다!】편안한 전신 데일리 스트레칭❤", 323, "스트레칭","https://www.youtube.com/watch?v=FccP2K6G_X0"),
("하루 한 번! 꼭 해야하는 10분 기본 전신근력 운동 홈트 (층간소음🙅🏻‍♀️)", 70, "전신","https://www.youtube.com/watch?v=aKzE3NNFEi4"),
("하루 두 번🧘🏻‍♀️반드시 해야하는 20분 전신순환 스트레칭 Ep.8 - 피로회복, 디톡스, 심신안정, 혈액순환, 라인정리 효과 (Whole body stretch)", 55, "스트레칭","https://www.youtube.com/watch?v=Kk7TQGqQ3nA"),
("하루 두 번🧘🏻‍♀️꼭 해야하는 25분 전신순환 스트레칭 Ep.9 - 피로회복, 독소제거, 심신안정, 혈액순환, 부종완화, 라인정리 효과 (Whole body stretch)", 54 ,"스트레칭", "https://www.youtube.com/watch?v=pcj9HY5KXb4"),
("하루 두 번🧘🏻‍♀️꼭 해야하는 25분 전신순환 스트레칭 Ep.12 - 초급 - 피로회복, 림프순환, 심신안정, 독소제거 (Whole body stretch)", 52,"스트레칭","https://www.youtube.com/watch?v=9C7jUnkT9sA"),
("하루 한 번! 꼭 해야하는 10분 기본 하체근력 운동 홈트 (층간소음🙅🏻‍♀️)",1000, "하체", "https://www.youtube.com/watch?v=C4_2puAkxfs" ),
("(스쿼트, 런지 없는) 초급 하체 운동 10분 루틴 - 힘들진 않은데 자극이 너무 좋아요!!", 150 ,"하체","https://www.youtube.com/watch?v=orEGOGBRldQ"),
("10분컷으로 윗배 아랫배 '싹'털리는 루틴🔥 (플랭크 없음)" , 10005 , "하체","https://www.youtube.com/watch?v=7mN-cJsJq0U"),
("누워서 핸드폰 보고 있는 분🤳 뒹굴거리면서 뱃살 빼봐요👊 [누워서하는 운동/5분]ㅣ다노티비", 46,"하체", "https://www.youtube.com/watch?v=mVsORhCxNL0"),
("🔥’하체비만’의 ‘허벅지안쪽살’을 얇게 만드는 ‘7가지 동작’ / 급속버전🔥", 489,"하체","https://www.youtube.com/watch?v=8UIorRMppzA"),
("🦵하루 딱 5분, 이 운동을 매일 따라해 보세요! (하비탈출/5분 운동습관)ㅣ다노티비", 4761, "하체", "https://www.youtube.com/watch?v=OKyuFF3uh5o"),
("5분! 누워서 허벅지살 돌려깎기 [하체 마라맛🔥]", 1106, "하체","https://youtube.com/watch?v=M8Rmq9_998g"),
("하체비만 11자다리 최고의 운동 [하체 핵매운맛]",321,"하체" ,"https://www.youtube.com/watch?v=NDsjmxTROEo"),
("종아리 얇아지는 최고의 스트레칭 3가지", 1055000, "하체", "https://www.youtube.com/watch?v=2xmNzityfKI"),
("예쁜 일자어깨 승모근 스트레칭 BEST3 [거북목/굽은등/라운드숄더 교정]" , 140,"상체","https://www.youtube.com/watch?v=dJXZRZvqbYg");


Insert into Exercise (user_id,exercise_date, start_time,end_time, exercise_time,exercise_kind ) value ("ssafy", "2024-05-19", "15:05","16:05","60","달리기"),
("ssafy", "2024-05-20", "14:25","16:05","100","달리기"),
("ssafy", "2024-05-21", "14:05","16:05","120","숨쉬기"),
("ssafy", "2024-05-22", "13:05","16:05","180","전신운동"),
("ssafy", "2024-05-23", "12:05","16:05","240","달리기"),
("ssafy", "2024-05-17", "11:05","16:05","240","달리기"),
("ssafy", "2024-05-18", "12:05","16:05","240","달리기");

Insert into Videoask(user_id, user_name ,ask_title, link) value ("ssafy", "김싸피", "서서하는 유산소 홈트 영상 올려주세요","https://www.youtube.com/watch?v=sTX0C08SYBM");