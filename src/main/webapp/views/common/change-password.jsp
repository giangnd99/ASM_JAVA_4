<%--
  Created by IntelliJ IDEA.
  User: Computer
  Date: 11/27/2024
  Time: 3:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Yêu Cầu Lấy Lại Mật Khẩu</title>
    <jsp:include page="../../layout/head.jsp"/>
</head>
<body>
<jsp:include page="../../layout/header.jsp"/>
<div class="container">
    <div class="row justify-content-center align-items-center min-vh-100">
        <div class="col-md-8 col-lg-6">
            <div class="card shadow border-0">
                <div class="card-header bg-success text-white text-center">
                    <h4 class="mb-0">Đổi mật khẩu</h4>
                </div>
                <div class="card-body">
                    <form action="change-password" method="POST">
                        <div class="row">
                            <!-- Username -->
                            <div class="col-md-6 mb-3">
                                <label for="username" class="form-label">Username(Email)</label>
                                <input type="text" id="username" name="email" class="form-control"
                                       placeholder="Enter your username" required>
                            </div>
                            <!-- Current Password -->
                            <div class="col-md-6 mb-3">
                                <label for="currentPassword" class="form-label">Mật khẩu cũ</label>
                                <input type="password" id="currentPassword" name="currentPassword" class="form-control"
                                       placeholder="Enter current password" required>
                            </div>
                        </div>
                        <div class="row">
                            <!-- New Password -->
                            <div class="col-md-6 mb-3">
                                <label for="newPassword" class="form-label">Mật khẩu mới:</label>
                                <input type="password" id="newPassword" name="newPassword" class="form-control"
                                       placeholder="Enter new password" required>
                            </div>
                            <!-- Confirm New Password -->
                            <div class="col-md-6 mb-3">
                                <label for="confirmNewPassword" class="form-label">Xác nhận lại mật khẩu</label>
                                <input type="password" id="confirmNewPassword" name="confirmNewPassword"
                                       class="form-control" placeholder="Confirm new password" required>
                            </div>
                        </div>
                        <!-- Submit Button -->
                        <div class="d-grid">
                            <button type="submit" class="btn btn-success btn-lg">Gửi</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../../layout/footer.jsp"/>
</body>
</html>
