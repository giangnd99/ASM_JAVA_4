<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<footer class="tm-bg-gray pt-5 pb-3 tm-text-gray tm-footer">
    <div class="container-fluid tm-container-small">
        <div class="row">
            <div class="col-lg-6 col-md-12 col-12 px-5 mb-5">
                <h3 class="tm-text-primary mb-4 tm-footer-title">Entertainment video</h3>
                <p>Website hiển thị và lưu trữu các video cho người dùng</p>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-8 col-md-7 col-12 px-5 mb-3">
                Copyright 2024 FPT Polytechnic. All rights reserved.
            </div>
            <div class="col-lg-4 col-md-5 col-12 px-5 text-right">
                Designed by <a href="https://templatemo.com" class="tm-text-gray" rel="sponsored" target="_parent">Nguyễn
                Đằng Giang</a>
            </div>
        </div>
    </div>
</footer>
<script src="<c:url value='/js/plugins.js'/>"></script>
<script src="<c:url value="/js/bootstrap.bundle.js"/>"></script>
<script>
    $(window).on("load", function () {
        $('body').addClass('loaded');
    });
</script>
