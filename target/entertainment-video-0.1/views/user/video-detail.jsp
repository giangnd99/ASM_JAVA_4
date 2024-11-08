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
                    <div class="mr-4 mb-2">
                        <span class="tm-text-gray-dark">Lượt xem: </span><span
                            class="tm-text-primary">${video.views}</span>
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
                            <a href="${baseUri}/video-detail?action=like" class="btn btn-primary">Thích</a>
                        </div>
                    </c:if>
                    <c:if test="${favorite !=null }">
                        <div class="text-center m-2">
                            <a href="${baseUri}/video-detail?action=unlike" class="btn btn-primary">Bỏ Thích</a>
                        </div>

                    </c:if>
                    <div class="text-center m-2">
                        <a href="${baseUri}/video-detail?action=share" class="btn btn-primary">Chia sẻ</a>
                    </div>
                </div>
                <div>
                    <h3 class="tm-text-gray-dark mb-3">Tags</h3>
                    <a href="#" class="tm-text-primary mr-4 mb-2 d-inline-block">Cloud</a>
                    <a href="#" class="tm-text-primary mr-4 mb-2 d-inline-block">Bluesky</a>
                    <a href="#" class="tm-text-primary mr-4 mb-2 d-inline-block">Nature</a>
                    <a href="#" class="tm-text-primary mr-4 mb-2 d-inline-block">Background</a>
                    <a href="#" class="tm-text-primary mr-4 mb-2 d-inline-block">Timelapse</a>
                    <a href="#" class="tm-text-primary mr-4 mb-2 d-inline-block">Night</a>
                    <a href="#" class="tm-text-primary mr-4 mb-2 d-inline-block">Real Estate</a>
                </div>
            </div>
        </div>
    </div>
    <div class="row mb-4">
        <h2 class="col-12 tm-text-primary">
            Videos được thích
        </h2>
    </div>
    <div class="row mb-3 tm-gallery">
        <div class="col-xl-3 col-lg-4 col-md-6 col-sm-6 col-12 mb-5">
            <figure class="effect-ming tm-video-item">
                <img src="img/img-01.jpg" alt="Image" class="img-fluid">
                <figcaption class="d-flex align-items-center justify-content-center">
                    <h2>Hangers</h2>
                    <a href="#">View more</a>
                </figcaption>
            </figure>
            <div class="d-flex justify-content-between tm-text-gray">
                <span class="tm-text-gray-light">12 Oct 2020</span>
                <span>12,460 views</span>
            </div>
        </div>
        <div class="col-xl-3 col-lg-4 col-md-6 col-sm-6 col-12 mb-5">
            <figure class="effect-ming tm-video-item">
                <img src="img/img-02.jpg" alt="Image" class="img-fluid">
                <figcaption class="d-flex align-items-center justify-content-center">
                    <h2>Perfumes</h2>
                    <a href="#">View more</a>
                </figcaption>
            </figure>
            <div class="d-flex justify-content-between tm-text-gray">
                <span class="tm-text-gray-light">18 Oct 2020</span>
                <span>11,402 views</span>
            </div>
        </div>
        <div class="col-xl-3 col-lg-4 col-md-6 col-sm-6 col-12 mb-5">
            <figure class="effect-ming tm-video-item">
                <img src="img/img-03.jpg" alt="Image" class="img-fluid">
                <figcaption class="d-flex align-items-center justify-content-center">
                    <h2>Clocks</h2>
                    <a href="#">View more</a>
                </figcaption>
            </figure>
            <div class="d-flex justify-content-between tm-text-gray">
                <span class="tm-text-gray-light">16 Oct 2020</span>
                <span>9,906 views</span>
            </div>
        </div>
        <div class="col-xl-3 col-lg-4 col-md-6 col-sm-6 col-12 mb-5">
            <figure class="effect-ming tm-video-item">
                <img src="img/img-04.jpg" alt="Image" class="img-fluid">
                <figcaption class="d-flex align-items-center justify-content-center">
                    <h2>Plants</h2>
                    <a href="#">View more</a>
                </figcaption>
            </figure>
            <div class="d-flex justify-content-between tm-text-gray">
                <span class="tm-text-gray-light">12 Oct 2020</span>
                <span>16,100 views</span>
            </div>
        </div>
        <div class="col-xl-3 col-lg-4 col-md-6 col-sm-6 col-12 mb-5">
            <figure class="effect-ming tm-video-item">
                <img src="img/img-05.jpg" alt="Image" class="img-fluid">
                <figcaption class="d-flex align-items-center justify-content-center">
                    <h2>Morning</h2>
                    <a href="#">View more</a>
                </figcaption>
            </figure>
            <div class="d-flex justify-content-between tm-text-gray">
                <span class="tm-text-gray-light">24 Sep 2020</span>
                <span>16,008 views</span>
            </div>
        </div>
        <div class="col-xl-3 col-lg-4 col-md-6 col-sm-6 col-12 mb-5">
            <figure class="effect-ming tm-video-item">
                <img src="img/img-06.jpg" alt="Image" class="img-fluid">
                <figcaption class="d-flex align-items-center justify-content-center">
                    <h2>Pinky</h2>
                    <a href="#">View more</a>
                </figcaption>
            </figure>
            <div class="d-flex justify-content-between tm-text-gray">
                <span class="tm-text-gray-light">21 Sep 2020</span>
                <span>12,860 views</span>
            </div>
        </div>
        <div class="col-xl-3 col-lg-4 col-md-6 col-sm-6 col-12 mb-5">
            <figure class="effect-ming tm-video-item">
                <img src="img/img-07.jpg" alt="Image" class="img-fluid">
                <figcaption class="d-flex align-items-center justify-content-center">
                    <h2>Bus</h2>
                    <a href="#">View more</a>
                </figcaption>
            </figure>
            <div class="d-flex justify-content-between tm-text-gray">
                <span class="tm-text-gray-light">18 Sep 2020</span>
                <span>10,900 views</span>
            </div>
        </div>
        <div class="col-xl-3 col-lg-4 col-md-6 col-sm-6 col-12 mb-5">
            <figure class="effect-ming tm-video-item">
                <img src="img/img-08.jpg" alt="Image" class="img-fluid">
                <figcaption class="d-flex align-items-center justify-content-center">
                    <h2>New York</h2>
                    <a href="#">View more</a>
                </figcaption>
            </figure>
            <div class="d-flex justify-content-between tm-text-gray">
                <span class="tm-text-gray-light">9 Sep 2020</span>
                <span>11,300 views</span>
            </div>
        </div>
    </div> <!-- row -->
</div> <!-- container-fluid, tm-container-content -->
<jsp:include page="../../layout/footer.jsp"/>
</body>
</html>
