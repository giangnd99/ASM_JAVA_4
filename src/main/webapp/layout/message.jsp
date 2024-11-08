<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:choose>
    <c:when test="${not empty successMessage}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <strong>Success!</strong> ${successMessage}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:when>
    <c:when test="${not empty errorMessage}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <strong>Error!</strong> ${errorMessage}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:when>

    <c:when test="${not empty warningMessage}">
        <div class="alert alert-warning alert-dismissible fade show" role="alert">
            <strong>Warning!</strong> ${warningMessage}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:when>

    <c:when test="${not empty infoMessage}">
        <div class="alert alert-info alert-dismissible fade show" role="alert">
            <strong>Info:</strong> ${infoMessage}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:when>
</c:choose>
