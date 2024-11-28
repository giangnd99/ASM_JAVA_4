<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="baseUri" value="${pageContext.request.contextPath}"/>
<div id="loader-wrapper">
    <div id="loader"></div>

    <div class="loader-section section-left"></div>
    <div class="loader-section section-right"></div>

</div>
<nav class="navbar navbar-expand-lg">
    <div class="container-fluid">
        <a class="navbar-brand" href="/home">
            <i class="fas fa-film mr-2"></i>
            Video Entertainment
        </a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <i class="fas fa-bars"></i>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ml-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link nav-link-1" aria-current="page" href="${baseUri}">Trang chủ</a>
                </li>

                <c:if test="${loggedUser == null}">
                    <li class="nav-item">
                        <a class="nav-link nav-link-2" href="${baseUri}/current-video">Videos vừa xem</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link nav-link-3 dropdown-toggle" href="" role="button"
                           data-bs-toggle="dropdown"
                           aria-expanded="false">
                            Tài khoản
                        </a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="${baseUri}/login">Đăng nhập</a></li>
                            <li><a class="dropdown-item" href="${baseUri}/register">Đăng ký</a></li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li><a class="dropdown-item" href="${baseUri}/forgot-password">Quên mật khẩu</a></li>
                        </ul>
                    </li>
                </c:if>
                <c:if test="${loggedUser != null}">

                    <li class="nav-item dropdown">
                        <a class="nav-link nav-link-3 dropdown-toggle" href="" role="button"
                           data-bs-toggle="dropdown"
                           aria-expanded="false">
                            Video
                        </a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="${baseUri}/favorite-video">Videos bạn yêu thích</a></li>
                        </ul>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link nav-link-3 dropdown-toggle" href="${baseUri}/account-setting" role="button"
                           data-bs-toggle="dropdown"
                           aria-expanded="false">Xin Chào, ${loggedUser.fullname}</a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="${baseUri}/account-setting">Thông
                                tin</a></li>
                            <li><a class="dropdown-item" href="${baseUri}/change-password">Đổi
                                mật khẩu</a></li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li><a class="dropdown-item" href="${baseUri}/logout">Đăng Xuất</a></li>
                        </ul>
                    </li>
                </c:if>

            </ul>
        </div>
    </div>
</nav>

<div class="tm-hero d-flex justify-content-center align-items-center" data-parallax="scroll"
     data-image-src="img/hero.jpg">
    <form class="d-flex tm-search-form" action="${pageContext.request.contextPath}/search">
        <input class="form-control tm-search-input" name="search" type="search" placeholder="Search"
               aria-label="Search">
        <button class="btn btn-outline-success tm-search-btn" type="submit">
            <i class="fas fa-search"></i>
        </button>
    </form>
</div>
<jsp:include page="message.jsp"/>
