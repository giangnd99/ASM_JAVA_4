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
    <title>${video.title}</title>
    <jsp:include page="../../layout/head.jsp"/>
</head>
<body>
<jsp:include page="../../layout/header.jsp"/>
<c:set var="baseUri" value="${pageContext.request.contextPath}"/>
<div class="container-fluid tm-container-content tm-mt-60">
    <div class="row mb-4">
        <h2 class="col-12 tm-text-primary">${video.title}</h2>
    </div>
    <div class="row tm-mb-90">
        <div class="col-xl-8 col-lg-7 col-md-6 col-sm-12">
            <iframe style="height: 100%" id="tm-video" src="https://www.youtube.com/embed/${video.href}"></iframe>
        </div>
        <div class="col-xl-4 col-lg-5 col-md-6 col-sm-12">
            <div class="tm-bg-gray tm-video-details">
                <div class="mb-4">
                    <h3 class="tm-text-gray-dark mb-3">Mô tả</h3>
                    <p> ${video.description}</p>
                </div>
                <div class="mb-4 d-flex flex-wrap">
                    <div class="mr-4 m-3">
                        <span class="tm-text-gray-dark">Lượt xem: </span><span
                            class="tm-text-primary">${video.views}</span>
                    </div>
                    <div class="mr-4 m-3">
                        <span class="tm-text-gray-dark">Lượt thích: </span><span
                            class="tm-text-primary">${likeCount}</span>
                    </div>
                    <div class="mr-4 m-3">
                        <span class="tm-text-gray-dark">Lượt share: </span><span
                            class="tm-text-primary">${shareCount}</span>
                    </div>
                </div>
                <c:if test="${favorite != null}">
                    <input type="text" name="favoriteId" hidden="hidden" value="${favorite.id}">
                    <div class="mb-4 d-flex flex-wrap">
                        <div class="mr-4 mb-2">
                            <span class="tm-text-gray-dark">Ngày thích: </span><span
                                class="tm-text-primary">${favorite.likedDate}</span>
                        </div>
                    </div>
                </c:if>
                <c:if test="${share != null}">
                    <div class="mb-4 d-flex flex-wrap">
                        <div class="mr-4 mb-2">
                            <span class="tm-text-gray-dark">Ngày chia sẽ: </span><span
                                class="tm-text-primary">${share.shareDate}</span>
                        </div>
                    </div>
                </c:if>
                <div class="d-inline-flex align-content-center">
                    <c:if test="${favorite ==null }">
                        <div class="text-center m-2">
                            <a href="${baseUri}/video-detail?action=like&id=${video.href}"
                               class="btn btn-primary">Thích</a>
                        </div>
                    </c:if>
                    <c:if test="${favorite !=null }">
                        <div class="text-center m-2">
                            <a href="${baseUri}/video-detail?action=unlike&id=${video.href}" class="btn btn-primary">Bỏ
                                Thích</a>
                        </div>

                    </c:if>
                    <div class="text-center m-2">
                        <a href="${baseUri}/video-detail?action=share" data-bs-toggle="modal" data-bs-target="#exampleModal" data-bs-whatever="@getbootstrap" class="btn btn-primary">Chia sẻ</a>

                        <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h1 class="modal-title fs-5" id="exampleModalLabel">New message</h1>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <form>
                                            <div class="mb-3">
                                                <label for="recipient-name" class="col-form-label">Recipient:</label>
                                                <input type="text" class="form-control" id="recipient-name">
                                            </div>
                                            <div class="mb-3">
                                                <label for="message-text" class="col-form-label">Message:</label>
                                                <textarea class="form-control" id="message-text"></textarea>
                                            </div>
                                        </form>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                        <button type="button" class="btn btn-primary">Send message</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row mb-4">
        <h2 class="col-12 tm-text-primary">
            5 Videos được xem nhiều nhất
        </h2>
    </div>
    <div class="row mb-3 tm-gallery">
        <c:forEach var="video" items="${top5Videos}">
        <div class="col-xl-3 col-lg-4 col-md-6 col-sm-6 col-12 mb-5">
            <figure class="effect-ming tm-video-item">
                <img src="${video.poster}" alt="Image" class="img-fluid">
                <figcaption class="d-flex align-items-center justify-content-center">
                    <h2>${video.title}</h2>
                    <a href="${baseUri}/video-detail?id=${video.href}">Xem thêm</a>
                </figcaption>
            </figure>
            <div class="d-flex justify-content-between tm-text-gray">
                <span>${video.views} views</span>
            </div>
        </div>
        </c:forEach>
    </div> <!-- row -->
</div> <!-- container-fluid, tm-container-content -->
<jsp:include page="../../layout/footer.jsp"/>
</body>
</html>
