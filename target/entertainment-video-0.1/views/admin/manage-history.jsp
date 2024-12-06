<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Video Management</title>
    <jsp:include page="head.jsp"/>
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/responsive/2.5.3/css/responsive.dataTables.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"/>
</head>
<body class="bg-light">
<div class="container d-flex mb-5">
    <!-- Sidebar -->
    <jsp:include page="side-bar.jsp"/>
    <!-- Main Content -->
    <div class="container-fluid px-4 my-4">
        <!-- Filters -->
        <div class="filters mb-4">
            <div class="row">
                <div class="col-md-4">
                    <label for="videoFilter" class="form-label">Video Title</label>
                    <select id="videoFilter" class="form-select">
                        <option value="all" selected>All Videos</option>
                        <!-- Populate this dynamically -->
                    </select>
                </div>
            </div>
        </div>

        <!-- Tabs -->
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
                    <table id="table-favo" class="table table-bordered text-center">
                        <thead class="table-light">
                        <tr>
                            <th>Video Title</th>
                            <th>Favorite Count</th>
                            <th>Latest Date</th>
                            <th>Oldest Date</th>
                        </tr>
                        </thead>
                        <tbody id="favoritesTableBody">
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- Favorite Users Section -->
            <div class="tab-pane fade" id="favorite-users-tab-pane" role="tabpanel"
                 aria-labelledby="favorite-users-tab">
                <div class="table-responsive mt-3">
                    <table id="table-user-favo" class="table table-bordered text-center">
                        <thead class="table-light">
                        <tr>
                            <th>Username</th>
                            <th>Fullname</th>
                            <th>Email</th>
                            <th>Favorite Date</th>
                        </tr>
                        </thead>
                        <tbody id="favoriteUsersTableBody">
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- Shared Friends Section -->
            <div class="tab-pane fade" id="shared-friends-tab-pane" role="tabpanel"
                 aria-labelledby="shared-friends-tab">
                <div class="table-responsive mt-3">
                    <table id="table-share" class="table table-bordered text-center">
                        <thead class="table-light">
                        <tr>
                            <th>Sender Name</th>
                            <th>Sender Email</th>
                            <th>Receiver Email</th>
                            <th>Send Date</th>
                        </tr>
                        </thead>
                        <tbody id="sharedFriendsTableBody">
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"/>
<script src="<c:url value="/js/api/HistoryAPI.js"/>"></script>
</body>
</html>
