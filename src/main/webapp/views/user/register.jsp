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
<section style="background-color: #eee; padding: 20px 20px">
    <div class="container h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-lg-12 col-xl-11">
                <div class="card text-black" style="border-radius: 25px;">
                    <div class="card-body p-md-1">
                        <div class="row justify-content-center">
                            <div class="col-md-10 col-lg-6 col-xl-5 order-2 order-lg-1">
                                <p class="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">Đăng kí</p>
                                <form action="${pageContext.request.contextPath}/register" method="POST"
                                      class="mx-1 mx-md-4">

                                    <!-- Tên của bạn -->
                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="form3Example1c">Tên của bạn</label>
                                        <input type="text" id="form3Example1c" class="form-control" name="fullname"/>
                                    </div>
                                    <!-- Email -->
                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="form3Example3c">Email của bạn</label>
                                        <input type="email" id="form3Example3c" class="form-control" name="email"/>
                                    </div>

                                    <!-- Số điện thoại -->
                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="phone">Số điện thoại của bạn</label>
                                        <input type="number" id="phone" class="form-control" name="phone"/>
                                    </div>

                                    <!-- Mật khẩu -->
                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="form3Example4c">Mật khẩu</label>
                                        <input type="password" id="form3Example4c" class="form-control"
                                               name="password"/>
                                    </div>
                                    <!-- Nút Đăng ký -->
                                    <div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
                                        <button type="submit" class="btn btn-primary btn-lg">Đăng kí</button>
                                    </div>
                                </form>

                            </div>

                            <!-- Hình ảnh bên cạnh -->
                            <div class="col-md-10 col-lg-6 col-xl-7 d-flex align-items-center order-1 order-lg-2">
                                <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-registration/draw1.webp"
                                     class="img-fluid" alt="Sample image">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<jsp:include page="../../layout/footer.jsp"/>
</body>
</html>
