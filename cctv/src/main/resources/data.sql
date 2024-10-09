/*
insert into user(user_id, email, name) values(100, "test100@test100.com", "test100");
insert into user(user_id, email, name) values(200, "test200@test200.com", "test200");

insert into video(video_id, user_id, name, description, url, created_at, location) values(100, 100, "video_100", "video_description_100", "100/video_url", now(), "video_location_100");
insert into video(video_id, user_id, name, description, url, created_at, location) values(200, 100, "video_200", "video_description_200", "200/video_url", now(), "video_location_200");

insert into video_log(log_id, video_id, content, time) values(100, 100, "video_100_log_100", now());
insert into video_log(log_id, video_id, content, time) values(200, 100, "video_100_log_200", now());
insert into video_log(log_id, video_id, content, time) values(300, 200, "video_200_log_300", now());

insert into video_summary(summary_id, video_id, title, content, created_at, modified_at) values(100, 100, "video_100_summary_title", "video_100_summary_content", now(), now());
insert into video_summary(summary_id, video_id, title, content, created_at, modified_at) values(200, 200, "video_200_summary_title", "video_200_summary_content", now(), now());

 */