<%--
  Created by IntelliJ IDEA.
  User: Computer
  Date: 11/6/2024
  Time: 9:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile-account</title>
    <jsp:include page="../../layout/head.jsp"/>
</head>
<body>
<jsp:include page="../../layout/header.jsp"/>
<div class="container-xl px-4 mt-4">
    <hr class="mt-0 mb-4">
    <div class="row">
        <div class="col-xl-4">
            <!-- Profile picture card-->
            <div class="card mb-4 mb-xl-0">
                <div class="card-header">Ảnh đại diện</div>
                <div class="card-body text-center">
                    <!-- Profile picture image-->
                    <img class="img-account-profile rounded-circle mb-2"
                         src="http://bootdey.com/img/Content/avatar/avatar1.png" alt="" width="200px">
                    <!-- Profile picture help block-->
                    <div class="small font-italic text-muted mb-4">JPG hoặc PNG độ lớn không quá 5 MB</div>
                    <!-- Profile picture upload button-->
                    <button class="btn btn-primary" type="button">Đăng ảnh</button>
                </div>
            </div>
        </div>
        <div class="col-xl-8">
            <!-- Account details card-->
            <div class="card mb-4">
                <div class="card-header">Thông tin tài khoản</div>
                <div class="card-body">
                    <form method="POST">
                        <!-- Form Group (username)-->
                        <div class="mb-3">
                            <label class="small mb-1" for="inputFullname">Họ và tên</label>
                            <input class="form-control" id="inputFullname" type="text" placeholder="Nhập họ và tên"
                                   value="${loggedUser.fullname}" name="fullname">
                        </div>
                        <!-- Form Group (email address)-->
                        <div class="mb-3">
                            <label class="small mb-1" for="inputEmailAddress">địa chỉ Email</label>
                            <input class="form-control" id="inputEmailAddress" type="email"
                                   placeholder="Nhập vào email" value="${loggedUser.email}" name="email">
                        </div>
                        <div class="mb-3">
                            <label class="small mb-1" for="inputUsername">Username</label>
                            <input class="form-control" id="inputUsername" type="text"
                                   placeholder="Nhập vào username" value="${loggedUser.username}" name="username">
                        </div>
                        <!-- Save changes button-->
                        <button class="btn btn-primary" type="submit"
                                formaction="${pageContext.request.contextPath}/account-setting">Lưu thay đổi
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../../layout/footer.jsp"/>
</body>
</html>
