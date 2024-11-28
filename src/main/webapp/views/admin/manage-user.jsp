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
<div class="container d-flex mb-5 p-4">
    <!-- Sidebar -->
    <jsp:include page="side-bar.jsp"/>
    <!-- Main Content -->
    <div class="container-fluid px-4 my-4">
        <!-- Tabs -->
        <ul class="nav nav-tabs" id="userTabs" role="tablist">
            <li class="nav-item m-0 p-3" role="presentation">
                <button class="nav-link active" id="user-edition-tab" data-bs-toggle="tab"
                        data-bs-target="#user-edition-tab-pane"
                        type="button" role="tab" aria-controls="user-edition-tab-pane" aria-selected="true">User Edition
                </button>
            </li>
            <li class="nav-item m-0 p-3" role="presentation">
                <button class="nav-link" id="user-list-tab" data-bs-toggle="tab" data-bs-target="#user-list-tab-pane"
                        type="button" role="tab" aria-controls="user-list-tab-pane" aria-selected="false">User List
                </button>
            </li>
        </ul>

        <!-- Tab Content -->
        <div class="tab-content" id="userTabContent">
            <!-- User Edition Form -->
            <div class="tab-pane fade show active" id="user-edition-tab-pane" role="tabpanel"
                 aria-labelledby="user-edition-tab" tabindex="0">
                <div class="row mt-3">
                    <div class="col-md-6">
                        <input id="id" type="text" hidden="hidden">

                        <label for="username" class="form-label">Username</label>
                        <input id="username" type="text" class="form-control mb-2" placeholder="Enter Username">

                        <label for="password" class="form-label">Password</label>
                        <input id="password" type="password" class="form-control mb-2"
                               placeholder="Enter Password">

                        <label for="fullname" class="form-label">Fullname</label>
                        <input id="fullname" type="text" class="form-control mb-2" placeholder="Enter Fullname">

                        <label for="email" class="form-label">Email Address</label>
                        <input id="email" type="email" class="form-control mb-2" placeholder="Enter Email Address">

                        <label for="role" class="form-label">Role</label>
                        <select id="role" class="form-select mb-2">
                            <option value="false">User</option>
                            <option value="true">Admin</option>
                        </select>

                        <label class="form-label">Active</label>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="active" value="true" id="active-true">
                            <label class="form-check-label" for="active-true">Active</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="active" value="false" id="active-false">
                            <label class="form-check-label" for="active-false">Off</label>
                        </div>

                    </div>
                </div>

                <!-- Action Buttons -->
                <div class="text-center mt-3 action-buttons">
                    <button id="saveBtn" class="btn btn-success">Save</button>
                    <button id="updateBtn" class="btn btn-warning" hidden>Update</button>
                    <button id="deleteBtn" class="btn btn-secondary" hidden>Delete</button>
                    <button id="resetBtn" type="reset" class="btn btn-success">Reset</button>
                </div>
            </div>

            <!-- User List Section -->
            <div class="tab-pane fade" id="user-list-tab-pane" role="tabpanel" aria-labelledby="user-list-tab"
                 tabindex="0">
                <div class="table-responsive mt-3">
                    <table id="userTable" class="table table-bordered text-center">
                        <thead class="table-light">
                        <tr>
                            <th>Username</th>
                            <th>Password</th>
                            <th>Fullname</th>
                            <th>Email</th>
                            <th>Role</th>
                            <th>Active</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        <!-- Rows will be dynamically populated -->
                        </tbody>
                    </table>
                </div>
                <div class="text-center">
                    <p id="userCount">0 users</p>
                    <nav aria-label="Page navigation">
                        <ul class="pagination justify-content-center">
                            <li class="page-item" id="prev-page">
                                <a class="page-link" href="#" aria-label="Previous">
                                    <span aria-hidden="true">«</span>
                                </a>
                            </li>
                            <li class="page-item" id="prev-page-ellipsis">
                                <a class="page-link" href="#" aria-label="Previous">
                                    <span aria-hidden="true">‹</span>
                                </a>
                            </li>
                            <li class="page-item" id="next-page-ellipsis">
                                <a class="page-link" href="#" aria-label="Next">
                                    <span aria-hidden="true">›</span>
                                </a>
                            </li>
                            <li class="page-item" id="next-page">
                                <a class="page-link" href="#" aria-label="Next">
                                    <span aria-hidden="true">»</span>
                                </a>
                            </li>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"/>
<c:url var="api" value="/js/api/UserAPI.js"/>
<script src="${api}"></script>
</body>
</html>
