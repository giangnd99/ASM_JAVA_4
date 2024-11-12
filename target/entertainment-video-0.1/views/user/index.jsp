<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Computer
  Date: 11/5/2024
  Time: 6:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Entertainment video</title>
    <jsp:include page="../../layout/head.jsp"/>
</head>
<body>
<jsp:include page="../../layout/header.jsp"/>
<c:set var="baseUri" value="${pageContext.request.contextPath}"/>
<div class="container-fluid tm-container-content tm-mt-60">
    <div class="row mb-4">
        <h2 class="col-6 tm-text-primary">Tất cả</h2>
    </div>
    <div class="row tm-mb-90 tm-gallery">
        <c:forEach var="video" items="${videos}">
            <div class="col-xl-3 col-lg-4 col-md-6 col-sm-6 col-12 mb-5">
                <figure class="effect-ming tm-video-item">
                    <img src="${video.poster}" alt="Image" class="img-fluid"/>
                    <figcaption
                            class="d-flex align-items-center justify-content-center"
                    >
                        <h2>${video.title}</h2>
                        <a href="${baseUri}/video-detail?id=${video.href}">Xem thêm</a>
                    </figcaption>
                </figure>
                <div class="d-flex justify-content-between tm-text-gray">
                    <span class="tm-text-gray-light">18 Oct 2020</span>
                    <span>${video.views} views</span>
                </div>
            </div>
        </c:forEach>
    </div>
    <!-- row -->
    <div class="row tm-mb-90">
        <div class="col-12 d-flex justify-content-between align-items-center tm-paging-col">
            <c:if test="${totalPages >= 1}">
                <c:set var="pageNumber" value="${pageNumber}"/>

                <!-- Nút Lùi -->
                <a href="${prevPageHref}" class="btn btn-primary tm-btn-prev mb-2 ${prevPageClass}">Lùi</a>

                <!-- Liên kết các trang -->
                <div class="tm-paging d-flex">
                    <c:forEach var="i" items="${pageLinks}">
                        <c:set var="pageLinkClass" value="${pageNumber == i ? 'active' : ''}"/>
                        <a class="tm-paging-link ${pageLinkClass}" href="${baseUri}/home?pageNumber=${i}">${i}</a>
                    </c:forEach>
                </div>

                <!-- Nút Tiếp -->
                <a href="${nextPageHref}" class="btn btn-primary tm-btn-next ${nextPageClass}">Tiếp</a>
            </c:if>

        </div>
    </div>
</div>

<jsp:include page="../../layout/footer.jsp"/>
</body>
</html>
