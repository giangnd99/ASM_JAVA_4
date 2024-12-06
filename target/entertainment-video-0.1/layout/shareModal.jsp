<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="exampleModalLabel">Nhập các email mà bạn muốn chia sẻ</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="mb-3">
                        <label for="recipient-name" class="col-form-label">Recipients: </label>
                        <input name="emails" type="text" class="form-control"
                               id="recipient-name"
                               placeholder="Nhập email cách nhau bởi dấu phẩy">
                        <input name="id" type="text" value="${param.href}" class="form-control"
                               hidden="hidden">
                        <input hidden="hidden" name="action" value="share">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                            Đóng
                        </button>
                        <button type="submit"
                                formaction="${pageContext.request.contextPath}/video-detail?id=${param.href}&action=share"
                                class="btn btn-success">Gửi
                        </button>
                    </div>
                </form>
            </div>

        </div>
    </div>
</div>