create table note_file
(
    file_id    int auto_increment comment '파일 번호'
        primary key,
    user_id    int                                  null comment '유저 번호',
    folder_id  int                                  null comment '부모 폴더 번호',
    file_name  varchar(80)                          null comment '파일 이름',
    created_at datetime default current_timestamp() not null comment '생성 날짜',
    updated_at datetime default current_timestamp() not null on update current_timestamp() comment '수정 날짜',
    constraint file_folder_folder_id_fk
        foreign key (folder_id) references note_folder (folder_id),
    constraint file_user_user_id_fk
        foreign key (user_id) references user (user_id)
)
    comment '파일 테이블';

create table note_folder
(
    folder_id        int auto_increment comment '폴더 번호'
        primary key,
    user_id          int                                  not null comment '유저 번호',
    parent_folder_id int                                  null comment '상위 폴더 번호',
    folder_name      varchar(80)                          not null comment '폴더 이름',
    created_at       datetime default current_timestamp() not null comment '생성 날짜',
    updated_at       datetime default current_timestamp() not null on update current_timestamp() comment '수정 날짜',
    constraint folder_folder_folder_id_fk
        foreign key (parent_folder_id) references note_folder (folder_id),
    constraint folder_user_user_id_fk
        foreign key (user_id) references user (user_id)
)
    comment '폴더 정보 테이블';

create table script
(
    script_id    int auto_increment comment '스크립트 번호'
        primary key,
    user_id      int                                    not null comment '유저 번호',
    file_id      int                                    null comment '파일 번호',
    category_id  int                                    null comment '카테고리 번호',
    title        varchar(100)                           null comment '스크립트 제목',
    is_recording tinyint(1) default 0                   not null comment '녹음 중 여부',
    is_liked     tinyint(1) default 0                   not null comment '즐겨찾기 여부',
    audio_file   varchar(300)                           null comment '오디오 파일 경로',
    video_file   varchar(300)                           null comment '비디오 파일 경로',
    created_at   datetime   default current_timestamp() not null comment '생성 날짜',
    updated_at   datetime   default current_timestamp() not null on update current_timestamp() comment '수정 날짜',
    constraint script_file_file_id_fk
        foreign key (file_id) references note_file (file_id),
    constraint script_script_category_category_id_fk
        foreign key (category_id) references script_category (category_id),
    constraint script_user_user_id_fk
        foreign key (user_id) references user (user_id)
)
    comment '스크립트 테이블';

create table script_category
(
    category_id   int auto_increment comment '카테고리 번호'
        primary key,
    category_name varchar(20)                          null comment '카테고리 이름',
    created_at    datetime default current_timestamp() not null comment '생성 날짜',
    updated_at    datetime default current_timestamp() not null on update current_timestamp() comment '수정 날짜'
)
    comment '스크립트 카테고리 테이블';

create table script_keyword
(
    keyword_id int auto_increment comment '키워드 번호'
        primary key,
    script_id  int                                  not null comment '스크립트 번호',
    keyword    varchar(30)                          not null comment '키워드명',
    created_at datetime default current_timestamp() not null comment '생성 날짜',
    updated_at datetime default current_timestamp() not null on update current_timestamp() comment '수정 날짜',
    constraint script_keyword_script_script_id_fk
        foreign key (script_id) references script (script_id)
)
    comment '스크립트 키워드 테이블';

create table script_paragraph
(
    paragraph_id       int auto_increment comment '단락 번호'
        primary key,
    script_id          int                                    not null comment '스크립트 번호',
    paragraph_sequence int                                    not null comment '단락 순서',
    start_time         varchar(100)                           not null comment '단락 시작 시간',
    end_time           varchar(100)                           not null comment '단락 종료 시간',
    paragraph_content  varchar(1000)                          null comment '스크립트 단락 내용',
    memo_content       varchar(1000)                          null comment '메모 내용',
    is_bookmarked      tinyint(1) default 0                   not null comment '북마크 여부',
    created_at         datetime   default current_timestamp() not null comment '생성 날짜',
    updated_at         datetime   default current_timestamp() not null on update current_timestamp() comment '수정 날짜',
    constraint script_paragraph_script_script_id_fk
        foreign key (script_id) references script (script_id)
)
    comment '스크립트 단락 테이블';

create table user
(
    user_id     int auto_increment comment '유저 번호'
        primary key,
    type_id     int                                     null comment '유저 유형 번호',
    email       varchar(50)                             not null comment '로그인 이메일',
    password    varchar(64)                             not null comment '비밀번호',
    level       int         default 1                   not null comment '유저 권한 단계',
    nickname    varchar(12)                             not null comment '닉네임',
    avatar      varchar(300)                            not null comment '아바타 이미지 경로',
    social_type varchar(10) default 'NORMAL'            not null comment '소셜로그인 계정 여부',
    created_at  datetime    default current_timestamp() not null comment '생성 날짜',
    updated_at  datetime    default current_timestamp() not null on update current_timestamp() comment '수정 날짜',
    constraint user_email_uindex
        unique (email),
    constraint user_user_type_type_id_fk
        foreign key (type_id) references user_type (type_id)
)
    comment '유저 정보 테이블';

create table user_type
(
    type_id    int auto_increment comment '유저 유형 번호'
        primary key,
    type_name  varchar(30)                          not null comment '유저 유형 이름',
    created_at datetime default current_timestamp() not null comment '생성 날짜',
    updated_at datetime default current_timestamp() not null on update current_timestamp() comment '수정 날짜'
)
    comment '유저 유형 테이블';