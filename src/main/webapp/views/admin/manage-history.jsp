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
        <!-- Tabs -->
        <nav class="navbar bg-body-tertiary">
            <div class="container-fluid">
                <form class="d-flex" role="search">
                    <div class="form-check-inline">
                        <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
                    </div>
                    <div class="form-floating me-2">
                        <select class="form-select" id="floatingSelect" aria-label="Floating label select example">
                            <option selected>Open this select menu</option>
                            <option value="1">One</option>
                            <option value="2">Two</option>
                            <option value="3">Three</option>
                        </select>
                        <label for="floatingSelect">Check by video title:</label>
                    </div>
                    <div class="form-floating me-2">
                        <button class="btn btn-outline-success" type="submit">Search</button>
                    </div>

                </form>
            </div>
        </nav>
        <ul class="nav nav-tabs" id="videoTab" role="tablist">
            <li class="nav-item m-0 p-3" role="presentation">
                <button class="nav-link active" id="favorites-tab" data-bs-toggle="tab"
                        data-bs-target="#favorites-tab-pane"
                        type="button" role="tab" aria-controls="favorites-tab-pane" aria-selected="true">Favorites
                </button>
            </li>
            <li class="nav-item m-0 p-3" role="presentation">
                <button class="nav-link" id="favorite-users-tab" data-bs-toggle="tab"
                        data-bs-target="#favorite-users-tab-pane"
                        type="button" role="tab" aria-controls="favorite-users-tab-pane" aria-selected="false">Favorite
                    Users
                </button>
            </li>
            <li class="nav-item m-0 p-3" role="presentation">
                <button class="nav-link" id="shared-friends-tab" data-bs-toggle="tab"
                        data-bs-target="#shared-friends-tab-pane"
                        type="button" role="tab" aria-controls="shared-friends-tab-pane" aria-selected="false">Shared
                    Friends
                </button>
            </li>
        </ul>

        <!-- Tab Content -->
        <div class="tab-content" id="videoTabContent">
            <!-- Favorites Section -->
            <div class="tab-pane fade show active" id="favorites-tab-pane" role="tabpanel"
                 aria-labelledby="favorites-tab">
                <div class="table-responsive mt-3">
                    <table class="table table-bordered text-center">
                        <thead class="table-light">
                        <tr>
                            <th>Video Title</th>
                            <th>Favorite Count</th>
                            <th>Latest Date</th>
                            <th>Oldest Date</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="history" items="${histories}">
                            <tr>
                                <td>${history.videoTitle}</td>
                                <td>${history.likeCount}</td>
                                <td>${history.likeDateLatest}</td>
                                <td>${history.likeDateOldest}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>

            </div>

            <!-- Favorite Users Section -->
            <div class="tab-pane fade" id="favorite-users-tab-pane" role="tabpanel"
                 aria-labelledby="favorite-users-tab">
                <div class="table-responsive mt-3">
                    <table class="table table-bordered text-center">
                        <thead class="table-light">
                        <tr>
                            <th>Video Title</th>
                            <th>Username</th>
                            <th>Fullname</th>
                            <th>Email</th>
                            <th>Favorite Date</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="history" items="${histories}">
                            <c:if test="${history.isFavoriteUserSection()}">
                                <tr>
                                    <td>${history.videoTitle}</td>
                                    <td>${history.username}</td>
                                    <td>${history.fullname}</td>
                                    <td>${history.email}</td>
                                    <td>${history.favoriteDate}</td>
                                </tr>
                            </c:if>
                        </c:forEach>

                        </tbody>
                    </table>
                </div>
            </div>

            <!-- Shared Friends Section -->
            <div class="tab-pane fade" id="shared-friends-tab-pane" role="tabpanel"
                 aria-labelledby="shared-friends-tab">
                <div class="table-responsive mt-3">
                    <table class="table table-bordered text-center">
                        <thead class="table-light">
                        <tr>
                            <th>Video Title</th>
                            <th>Sender Name</th>
                            <th>Sender Email</th>
                            <th>Receiver Email</th>
                            <th>Date Latest</th>
                            <th>Date Oldest</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="history" items="${histories}">
                            <c:if test="${history.isSharedFriendSection()}">
                                <tr>
                                    <td>${history.videoTitle}</td>
                                    <td>${history.senderName}</td>
                                    <td>${history.senderEmail}</td>
                                    <td>${history.receiverEmail}</td>
                                    <td>${history.shareDateLatest}</td>
                                    <td>${history.shareDateOldest}</td>
                                </tr>
                            </c:if>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>


</div>
<jsp:include page="footer.jsp"/>
</body>
</html>
