<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Manage Videos</title>
    <jsp:include page="head.jsp"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"/>
</head>
<body class="bg-light">
<div class="container d-flex mb-5 p-4">
    <!-- Sidebar -->
    <jsp:include page="side-bar.jsp"/>

    <!-- Content -->
    <div class="container-fluid p-4 my-4">
        <!-- Tabs -->
        <ul class="nav nav-tabs" id="videoTab" role="tablist">
            <li class="nav-item" role="presentation">
                <button class="nav-link active" id="edit-tab" data-bs-toggle="tab" data-bs-target="#edit-tab-pane"
                        type="button" role="tab" aria-controls="edit-tab-pane" aria-selected="true">Video Edition
                </button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="list-tab" data-bs-toggle="tab" data-bs-target="#list-tab-pane"
                        type="button" role="tab" aria-controls="list-tab-pane" aria-selected="false">Video List
                </button>
            </li>
        </ul>

        <!-- Tab Content -->
        <div class="tab-content" id="videoTabContent">
            <!-- Video Edition Form -->
            <div class="tab-pane fade show active" id="edit-tab-pane" role="tabpanel" aria-labelledby="edit-tab">
                <div class="row mt-3">
                    <div class="col-md-4 text-center">
                        <div class="border mb-3 p-3" style="border-radius: 8px;">
                            <p><strong>Poster</strong></p>
                            <img id="posterPreview"
                                 src="https://via.placeholder.com/150"
                                 alt="Poster preview"
                                 style=" width: 100%; object-fit: contain; background-color: #f9f9f9; border-radius: 8px;" />
                            <input type="text"
                                   id="poster"
                                   class="form-control mt-3"
                                   placeholder="Enter video href here..."
                                   onchange="updatePosterPreview(this.value)" />
                        </div>
                    </div>
                    <div class="col-md-8">
                        <form id="videoForm">
                            <input type="hidden" id="id">

                            <!-- YouTube ID -->
                            <label class="form-label">YouTube ID</label>
                            <input type="text" class="form-control mb-2" id="href" placeholder="Enter YouTube ID">

                            <!-- Title -->
                            <label class="form-label">Video Title</label>
                            <input type="text" class="form-control mb-2" id="title" placeholder="Enter Video Title">

                            <!-- Views -->
                            <label class="form-label">View Count</label>
                            <input type="number" class="form-control mb-2" id="views" value="0">

                            <!-- Status -->
                            <label class="form-label d-block">Status</label>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="active" id="statusActive"
                                       value="true" checked>
                                <label class="form-check-label" for="statusActive">Active</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="active" id="statusInactive"
                                       value="false">
                                <label class="form-check-label" for="statusInactive">Inactive</label>
                            </div>

                            <!-- Description -->
                            <label class="form-label mt-3">Description</label>
                            <textarea class="form-control" id="description" rows="3"
                                      placeholder="Enter description"></textarea>

                            <!-- Action Buttons -->
                            <div class="text-center mt-3 action-buttons">
                                <button type="button" class="btn btn-danger" id="createBtn">Create</button>
                                <button type="button" class="btn btn-warning" id="updateBtn" hidden>Update</button>
                                <button type="button" class="btn btn-secondary" id="deleteBtn" hidden>Delete</button>
                                <button type="button" class="btn btn-dark" id="resetBtn">Reset</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>


        <!-- Video List -->
            <div class="tab-pane fade" id="list-tab-pane" role="tabpanel" aria-labelledby="list-tab">
                <div class="table-responsive mt-3">
                    <table class="table table-bordered text-center" id="videoTable">
                        <thead class="table-light">
                        <tr>
                            <th>ID</th>
                            <th>Title</th>
                            <th>Poster</th>
                            <th>Views</th>
                            <th>Description</th>
                            <th>Active</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
                <!-- Pagination -->
                <div class="d-flex justify-content-center">
                    <ul class="pagination">
                        <li class="page-item" id="prev-page"><a class="page-link" href="#">Previous</a></li>
                        <li class="page-item" id="next-page"><a class="page-link" href="#">Next</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"/>
<script src="<c:url value="/js/api/VideoAPI.js"/>"></script>
</body>
</html>
