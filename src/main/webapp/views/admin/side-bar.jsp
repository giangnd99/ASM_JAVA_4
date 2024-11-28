<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="baseUri" value="${pageContext.request.contextPath}"/>
<h2 class="m-0">Dashboard</h2>
<div class="list-group list-group-flush my-3 offcanvas offcanvas-start fs-3" data-bs-scroll="true"
     data-bs-backdrop="false" tabindex="-1" id="offcanvasScrolling" aria-labelledby="offcanvasScrollingLabel">
    <div class="offcanvas-header">
        <h5 class="offcanvas-title" id="offcanvasScrollingLabel"><i class="fas fa-user-secret me-2">Giangnd</i></h5>
        <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
    </div>
    <a href="${baseUri}/admin/manage-video" class="list-group-item list-group-item-action bg-transparent second-text fw-bold">
        <i class="fas fa-film me-2"></i>Video
    </a>
    <a href="${baseUri}/admin/manage-user" class="list-group-item list-group-item-action bg-transparent second-text fw-bold">
        <i class="fas fa-user me-2"></i>User
    </a>
    <a href="${baseUri}/admin/manage-history" class="list-group-item list-group-item-action bg-transparent second-text fw-bold">
        <i class="fas fa-project-diagram me-2"></i>History
    </a>
    <a href="${baseUri}/logout" class="list-group-item list-group-item-action bg-transparent text-danger fw-bold">
        <i class="fas fa-power-off me-2"></i>Logout
    </a>
</div>

<nav class="navbar navbar-expand-lg navbar-light bg-transparent fixed-top fs-3">
    <button class="btn btn-outline-primary me-3" id="menu-toggle" data-bs-toggle="offcanvas"
            data-bs-target="#offcanvasScrolling" aria-controls="offcanvasScrolling">
        <i class="fas fa-align-left"></i>
    </button>
</nav>