<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Admin Dashboard</title>
    <jsp:include page="head.jsp"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
          integrity="sha384-k6RqeWeci5ZR/Lv4MR0sA0FfDOMeA4Zs7+2RYxxSO4yV5E5RkN1DEwH1nFVZ69I" crossorigin="anonymous"/>
</head>
<body>
<div class="container d-flex">
    <!-- Sidebar -->
    <jsp:include page="side-bar.jsp"/>
    <div class="container-fluid px-4">
        <!-- Thống kê Video -->
        <div class="row g-3 my-3">
            <div class="col-md-3">
                <div class="card p-3 text-center">
                    <h3 class="fs-2">150</h3>
                    <p>Videos</p>
                    <i class="fas fa-video fs-1 primary-text border rounded-circle p-3 bg-light"></i>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card p-3 text-center">
                    <h3 class="fs-2">50,200</h3>
                    <p>Total Views</p>
                    <i class="fas fa-eye fs-1 primary-text border rounded-circle p-3 bg-light"></i>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card p-3 text-center">
                    <h3 class="fs-2">30%</h3>
                    <p>Increase in Views</p>
                    <i class="fas fa-chart-line fs-1 primary-text border rounded-circle p-3 bg-light"></i>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card p-3 text-center">
                    <h3 class="fs-2">75</h3>
                    <p>Comments</p>
                    <i class="fas fa-comments fs-1 primary-text border rounded-circle p-3 bg-light"></i>
                </div>
            </div>
        </div>

        <!-- Video Mới Nhất -->
        <div class="row my-5">
            <h3 class="fs-4 mb-3">Recent Videos</h3>
            <div class="col">
                <table class="table table-bordered table-hover align-middle bg-white shadow-sm">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Video Title</th>
                        <th>Uploader</th>
                        <th>Views</th>
                        <th>Status</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>1</td>
                        <td>Exploring AI Technology</td>
                        <td>John Doe</td>
                        <td>5,000</td>
                        <td>Active</td>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td>Introduction to Machine Learning</td>
                        <td>Jane Smith</td>
                        <td>3,200</td>
                        <td>Active</td>
                    </tr>
                    <tr>
                        <td>3</td>
                        <td>Understanding Deep Learning</td>
                        <td>Emily Johnson</td>
                        <td>2,800</td>
                        <td>Inactive</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

</div>
<jsp:include page="footer.jsp"/>
</body>
</html>
