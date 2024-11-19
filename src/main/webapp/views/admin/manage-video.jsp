<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Admin Dashboard</title>
    <jsp:include page="head.jsp"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"/>
</head>
<body class="bg-light">
<div class="container d-flex">
    <!-- Sidebar -->
    <jsp:include page="side-bar.jsp"/>
    <!-- Trang nội dung -->

    <div class="container-fluid px-4 my-4">
        <div class="tm-hero d-flex justify-content-center align-items-center" data-parallax="scroll"
             data-image-src="img/hero.jpg">
            <form class="d-flex tm-search-form" action="${pageContext.request.contextPath}/admin/manage-video" method="post">
                <input class="form-control tm-search-input" name="search" type="search" placeholder="Search"
                       aria-label="Search">
                <button class="btn btn-outline-success tm-search-btn" type="submit">
                    <i class="fas fa-search"></i>
                </button>
            </form>
        </div>
        <!-- Tabs -->
        <ul class="nav nav-tabs" id="myTab" role="tablist">
            <li class="nav-item m-0 p-3" role="presentation">
                <button class="nav-link active" id="home-tab" data-bs-toggle="tab" data-bs-target="#home-tab-pane"
                        type="button" role="tab" aria-controls="home-tab-pane" aria-selected="true">Video Edition
                </button>
            </li>
            <li class="nav-item m-0 p-3" role="presentation">
                <button class="nav-link" id="profile-tab" data-bs-toggle="tab" data-bs-target="#profile-tab-pane"
                        type="button" role="tab" aria-controls="profile-tab-pane" aria-selected="false">Video List
                </button>
            </li>
        </ul>

        <!-- Tab Content -->
        <div class="tab-content" id="myTabContent">
            <!-- Video Edition Form -->
            <div class="tab-pane fade show active" id="home-tab-pane" role="tabpanel" aria-labelledby="home-tab"
                 tabindex="0">
                <div class="row mt-3">
                    <div class="col-md-4 text-center">
                        <div class="border mb-3 p-3" style="border-radius: 8px;">
                            <p><strong>Poster</strong></p>
                            <div style="height: 150px; background-color: #f9f9f9;"></div>
                        </div>
                    </div>
                    <div class="col-md-8">
                        <label class="form-label">YouTube ID?</label>
                        <input type="text" class="form-control mb-2" placeholder="Enter YouTube ID">

                        <label class="form-label">Video Title?</label>
                        <input type="text" class="form-control mb-2" placeholder="Enter Video Title">

                        <label class="form-label">View Count?</label>
                        <input type="number" class="form-control mb-2" value="0">

                        <label class="form-label d-block">Status</label>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="status" id="active" checked>
                            <label class="form-check-label" for="active">Active</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="status" id="inactive">
                            <label class="form-check-label" for="inactive">Inactive</label>
                        </div>

                        <label class="form-label mt-3">Description?</label>
                        <textarea class="form-control" rows="3" placeholder="Enter description"></textarea>
                    </div>
                </div>

                <!-- Action Buttons -->
                <div class="text-center mt-3 action-buttons">
                    <button class="btn btn-danger">Create</button>
                    <button class="btn btn-warning">Update</button>
                    <button class="btn btn-secondary">Delete</button>
                    <button class="btn btn-dark">Reset</button>
                </div>
            </div>

            <!-- Video List Section -->
            <div class="tab-pane fade" id="profile-tab-pane" role="tabpanel" aria-labelledby="profile-tab" tabindex="0">
                <div class="table-responsive mt-3">
                    <table class="table table-bordered text-center">
                        <thead class="table-light">
                        <tr>
                            <th>Video Title</th>
                            <th>YouTube ID</th>
                            <th>View Count</th>
                            <th>Like Count</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="video" items="${histories}">
                            <tr>
                                <td>${video.videoTitle}</td>
                                <td>${video.videoHref}</td>
                                <td>${video.viewCount}</td>
                                <td>${video.likeCount}</td>
                                <td>${video.isActive}</td>
                                <td><a href="#" class="text-primary">Edit</a></td>
                            </tr>
                        </c:forEach>
                        <!-- Additional rows can be added here -->
                        </tbody>
                    </table>
                </div>
                <div class="text-center">
                    <p>${videos.size()}</p>
                    <nav aria-label="Page navigation">
                        <ul class="pagination justify-content-center">
                            <li class="page-item"><a class="page-link" href="#">«</a></li>
                            <li class="page-item"><a class="page-link" href="#">‹</a></li>
                            <li class="page-item"><a class="page-link" href="#">›</a></li>
                            <li class="page-item"><a class="page-link" href="#">»</a></li>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>
