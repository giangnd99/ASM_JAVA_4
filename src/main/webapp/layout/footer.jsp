<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<footer class="p-3 text-light bg-dark text-center">
    © Copyright by PFT Polytechnic
</footer>
<script src="<c:url value='/js/plugins.js'/>"></script>
<script src="<c:url value="/js/bootstrap.bundle.js"/>"></script>
<script>
    $(window).on("load", function () {
        $('body').addClass('loaded');
    });
</script>
