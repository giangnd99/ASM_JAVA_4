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
        <div class="col-md-6 col-lg-5">
            <div class="card shadow border-0">
                <div class="card-header bg-success text-white text-center">
                    <h4 class="mb-0">Yêu cầu lấy lại mật khẩu</h4>
                </div>
                <div class="card-body">
                    <form action="forgot-password" method="POST">
                        <!-- Email Field -->
                        <div class="mb-3">
                            <label for="email" class="form-label">Email</label>
                            <input type="email" id="email" name="email" class="form-control"
                                   placeholder="Nhập email của bạn" required>
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
