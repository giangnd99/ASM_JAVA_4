<%--
  Created by IntelliJ IDEA.
  User: Computer
  Date: 11/14/2024
  Time: 9:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row tm-mb-90">
  <div class="col-12 d-flex justify-content-between align-items-center tm-paging-col">
    <c:if test="${totalPages >= 1}">
      <c:set var="pageNumber" value="${pageNumber}"/>

      <!-- Nút Lùi -->
      <a href="${prevPageHref}" class="btn btn-primary tm-btn-prev mb-2 ${prevPageClass}">Lùi</a>

      <!-- Liên kết các trang -->
      <div class="tm-paging d-flex">
        <c:forEach var="i" items="${pageLinks}">
          <c:set var="pageLinkClass" value="${pageNumber == i ? 'active' : ''}"/>
          <a class="tm-paging-link ${pageLinkClass}" href="${baseUri}/home?pageNumber=${i}">${i}</a>
        </c:forEach>
      </div>

      <!-- Nút Tiếp -->
      <a href="${nextPageHref}" class="btn btn-primary tm-btn-next ${nextPageClass}">Tiếp</a>
    </c:if>
  </div>
</div>
