-- Tạo database trong MySQL
DROP DATABASE if exists asmjava4;
CREATE DATABASE asmjava4;
USE asmjava4;

-- Tạo bảng user
CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fullname VARCHAR(10) NOT NULL unique,
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
    description NVARCHAR(255) NOT NULL,
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
    emails varchar(50),
    shareDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES user(id),
    FOREIGN KEY (videoId) REFERENCES video(id)
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
insert into video(title, href , description) values
(N'Duyên Do Trời, Phận Tại Ta','qi5d_OQFWpg',N'Voi Bản Đôn')

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
