<%--
  Created by IntelliJ IDEA.
  User: Computer
  Date: 11/6/2024
  Time: 7:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <jsp:include page="../../layout/head.jsp"/>
</head>
<body>
<jsp:include page="../../layout/header.jsp"/>
<div class="container-lg">
    <h1 class="text-center my-4">Đăng Nhập</h1>
    <div class="row justify-content-center">
        <div class="col-md-6">
            <form action="login" method="POST">
                <div class="mb-3">
                    <label for="email" class="form-label">Email</label>
                    <input type="email" class="form-control" id="email" name="email" required>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Mật khẩu</label>
                    <input type="password" class="form-control" id="password" name="password" required>
                </div>
                <div class="form-check mb-3">
                    <input type="checkbox" class="form-check-input" id="rememberMe" name="rememberMe">
                    <label class="form-check-label" for="rememberMe">Nhớ tài khoản</label>
                </div>
                <button type="submit" class="btn btn-primary w-100">Đăng Nhập</button>
            </form>

            <div class="text-center my-3">
                <a href="forgot_password" class="text-decoration-none">Quên mật khẩu?</a>
            </div>

            <div class="text-center">
                <span>Chưa có tài khoản?</span> <a href="register" class="text-decoration-none">Đăng ký ngay</a>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../../layout/footer.jsp"/>
</body>
</html>
