-- Tạo database trong MySQL
DROP DATABASE if exists asmjava4;
CREATE DATABASE asmjava4;
USE asmjava4;

-- Tạo bảng user
CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fullname VARCHAR(10) NOT NULL unique,
    username VARCHAR(10) NOT NULL unique,
    password VARCHAR(10) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    isAdmin BIT NOT NULL DEFAULT 0,
    isActive BIT NOT NULL DEFAULT 1
);

-- Tạo bảng video
CREATE TABLE video (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title NVARCHAR(255) NOT NULL,
    href VARCHAR(50) NOT NULL UNIQUE,
    poster VARCHAR(255),
    views INT NOT NULL DEFAULT 0,
    description text NOT NULL,
    isActive BIT NOT NULL DEFAULT 0
);

Drop table if exists favorite;
CREATE TABLE favorite (
    id INT AUTO_INCREMENT PRIMARY KEY,
    userId INT,
    videoId INT,
    viewedDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    likedDate DATETIME,
    FOREIGN KEY (userId) REFERENCES user(id),
    FOREIGN KEY (videoId) REFERENCES video(id)
);

Drop table if exists share;
CREATE TABLE share (
	id INT auto_increment PRIMARY KEY,
	userId INT,
    videoId INT,
    emails text,
    shareDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES user(id),
    FOREIGN KEY (videoId) REFERENCES video(id)
);
Drop table if exists logs;
CREATE TABLE logs (
	id Int auto_increment primary key,
    url varchar(50) not null,
    time timestamp not null DEFAULT CURRENT_TIMESTAMP,
    username varchar(255) not null,
    visitors int null default 0
);


insert into user(fullname,password,email,isAdmin) values
('Nguyen Van A', 'password1', 'nguyenvana@fpt.edu.vn', 1),
('Tran Thi B', 'password2', 'tranthib@fpt.edu.vn', 0),
('Le Van C', 'password3', 'levanc@fpt.edu.vn', 0),
('Pham Thi D', 'password4', 'phamthid@fpt.edu.vn', 1),
('Tran Van F', 'password6', 'tranvanf@gmail.com', 1),
('Le Thi G', 'password7', 'lethig@fpt.edu.vn', 0),
('Pham Van H', 'password8', 'phamvanh@fpt.edu.vn', 1),
('Nguyen Thi I', 'password9', 'nguyenthi@gmail.com', 0),
('Tran Thi DD', 'password30', 'tranthidd@gmail.com', 0);

insert into video(title, href, poster, description)
values (N'Duyên Do Trời, Phận Tại Ta', 'qi5d_OQFWpg', 'https://img.youtube.com/vi/qi5d_OQFWpg/maxresdefault.jpg',
        N'Voi Bản Đôn')
     , (N'Lộ trình Java Spring boot 3 năm 2024 hiệu quả nhất', 'H2gquNz1bvs',
        'https://img.youtube.com/vi/H2gquNz1bvs/maxresdefault.jpg', N'Giới thiệu khóa học và lộ trình học Spring boot 3 đầy đủ và hiệu quả nhất cho người mới bắt đầu.

Link tải về file roadmap: https://github.com/devteria/document')
     , (N'Khóa học Java spring boot 3: #1 Hello world', 'MiHVcukru3U',
        'https://img.youtube.com/vi/MiHVcukru3U/maxresdefault.jpg', N'Phần 1: cài đặt các công cụ cần thiết và tạo ứng dụng web application đầu tiên bằng Spring boot 3

Các bạn có thể đăng ký trở thành hội viên để ủng hộ kênh và tiếp cận những video đặc biệt dành riêng cho hội viên:
   / @devteriachannel

Group on Facebook:
www.facebook.com/groups/devteria/
')
     , (N'Hướng dẫn cài đặt Docker và MySQL trên Docker', 'Oa7bpIZ6RxI',
        'https://img.youtube.com/vi/Oa7bpIZ6RxI/maxresdefault.jpg',
        N'Docker là một công cụ rất quan trọng đối với developer ở thời điểm hiện tại. Việc nắm bắt và sử dụng thành thạo Docker gần như là một yêu cầu bắt buộc. Hôm nay chúng ta sẽ tìm hiểu cách cài đặt Docker và MySQL trên Docker.')
     , (N'Khóa học Java spring boot 3: #2 Kết nối Database với Spring JPA và tạo API CRUD', 'O-3tJAVlwQE',
        'https://img.youtube.com/vi/O-3tJAVlwQE/maxresdefault.jpg', N'Khoá học Java Spring boot 3

Kết nối Database với Spring JPA và tạo API CRUD.')
     , (N'Khóa học Java spring boot 3: #3 Quản lý Exception tập trung và Validation', 'dN4Ifu-4PAs',
        'https://img.youtube.com/vi/a9wMrj-JHV4/maxresdefault.jpg', N'Khoá học Java Spring boot 3

Quản lý exception tập trung và validation trong Spring')
     , (N'Khóa học Java spring boot 3: #4 Quản lý Exception nâng cao và cách chuẩn hóa API Response đúng cách',
        'a9wMrj-JHV4', 'https://img.youtube.com/vi/a9wMrj-JHV4/maxresdefault.jpg',
        N'Quản lý Exception nâng cao và cách chuẩn hóa API Response một cách chuyên nghiệp trong môi trường thực tế.')
     , (N'Tích hợp VN PAY vào BE viết bằng Java Spring Boot', 'UfdfjRN0iHw',
        'https://img.youtube.com/vi/UfdfjRN0iHw/maxresdefault.jpg',
        N'Áp dụng vào dự án một cách chuyên nghiệp trong môi trường thực tế.')
     , (N'Tất cả các thuật toán Machine Learning trong 23 phút', 'ULE78ME1ckQ',
        'https://img.youtube.com/vi/ULE78ME1ckQ/maxresdefault.jpg',
        N'Trong tutorial này, mình đã tổng hợp đầy đủ các thuật toán phổ biến trong Machine Learning, từ những thuật toán cơ bản nhất đến những thuật toán phức tạp hơn. Dù bạn là người mới bắt đầu hay đã có kinh nghiệm, video này sẽ giúp bạn nắm vững các thuật toán quan trọng và hiểu được cách ứng dụng của chúng trong các bài toán thực tế')
     , (N'Tất tần tật về Neural Network trong 12 phút', 'sWPNm_GhhCA',
        'https://img.youtube.com/vi/sWPNm_GhhCA/maxresdefault.jpg', N'Trong tutorial này mình sẽ giải thích cách Neural Network được xây dựng và vận hành theo hướng tiếp cận bottom-up (đi từ các thành phần cơ bản, rồi kết hợp lại thành mô hình hoàn chỉnh). Để đảm bảo ai cũng có thể hiểu được thì mình sẽ chỉ giải thích hoàn toàn bằng cách kiến thức toán phổ thông rất cơ bản (cơ bản đến mức các bạn sẽ không thể quên được 😂)

Trang web chính của mình: https://www.viet-it.com/');
insert into history(userID,videoID,isLiked,likedDate) values
(1, 1 , 1,date())
-- Tạo thủ tục lưu trữ để truy xuất danh sách người dùng đã like video theo href
DELIMITER //
CREATE PROCEDURE sp_selectUsersLikedVideoByHref(IN videoHref VARCHAR(50))
BEGIN
    SELECT u.id, u.password, u.isAdmin, u.isActive, u.username, u.email
    FROM video v
    LEFT JOIN history h ON h.videoId = v.id
    LEFT JOIN user u ON u.id = h.userId
    WHERE v.href = videoHref AND u.isActive = 1 AND v.isActive = 1 AND h.isLiked = 1;
END //
DELIMITER ;
