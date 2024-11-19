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
        <ul class="nav nav-tabs" id="myTab" role="tablist">
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
        <div class="tab-content" id="myTabContent">
            <!-- User Edition Form -->
            <div class="tab-pane fade show active" id="user-edition-tab-pane" role="tabpanel"
                 aria-labelledby="user-edition-tab"
                 tabindex="0">
                <div class="row mt-3">
                    <div class="col-md-6">
                        <label class="form-label">Username?</label>
                        <input type="text" class="form-control mb-2" placeholder="Enter Username">

                        <label class="form-label">Password?</label>
                        <input type="password" class="form-control mb-2" placeholder="Enter Password">

                        <label class="form-label">Fullname?</label>
                        <input type="text" class="form-control mb-2" placeholder="Enter Fullname">

                        <label class="form-label">Email Address?</label>
                        <input type="email" class="form-control mb-2" placeholder="Enter Email Address">
                    </div>
                </div>

                <!-- Action Buttons -->
                <div class="text-center mt-3 action-buttons">
                    <button class="btn btn-warning">Update</button>
                    <button class="btn btn-secondary">Delete</button>
                </div>
            </div>

            <!-- User List Section -->
            <div class="tab-pane fade" id="user-list-tab-pane" role="tabpanel" aria-labelledby="user-list-tab"
                 tabindex="0">
                <div class="table-responsive mt-3">
                    <table class="table table-bordered text-center">
                        <thead class="table-light">
                        <tr>
                            <th>Username</th>
                            <th>Password</th>
                            <th>Fullname</th>
                            <th>Email</th>
                            <th>Role</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>TeoNV</td>
                            <td>123456</td>
                            <td>Nguyễn Văn Tèo</td>
                            <td>teonv@gmail.com</td>
                            <td>Admin</td>
                            <td><a href="#" class="text-primary">Edit</a></td>
                        </tr>
                        <!-- Additional rows can be added here -->
                        </tbody>
                    </table>
                </div>
                <div class="text-center">
                    <p>185 users</p>
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
