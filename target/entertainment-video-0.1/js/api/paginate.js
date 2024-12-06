$(document).ready(function () {
    $('#table-favo').DataTable({
        "paging": true, // Bật phân trang
        "lengthChange": false, // Tắt tính năng thay đổi số dòng hiển thị
        "searching": true, // Bật tính năng tìm kiếm
        "ordering": true, // Bật sắp xếp cột
        "info": true, // Hiển thị thông tin về số dòng
        "autoWidth": false, // Không tự động điều chỉnh chiều rộng cột
        "responsive": true, // Bật chế độ phản hồi
        "columns": [
            {"data": "title"},
            {"data": "Favorite Count"},
            {"data": "Latest Date"},
            {"data": "Oldest Date"},
        ]
    });
    $('#table-user-favo').DataTable({
        "paging": true, // Bật phân trang
        "lengthChange": false, // Tắt tính năng thay đổi số dòng hiển thị
        "searching": true, // Bật tính năng tìm kiếm
        "ordering": true, // Bật sắp xếp cột
        "info": true, // Hiển thị thông tin về số dòng
        "autoWidth": false, // Không tự động điều chỉnh chiều rộng cột
        "responsive": true, // Bật chế độ phản hồi
        "columns": [
            {"data": "Username"},
            {"data": "Fullname"},
            {"data": "Email"},
            {"data": "Favorite Date"},
        ]
    });
    $('#table-share').DataTable({
        "paging": true, // Bật phân trang
        "lengthChange": false, // Tắt tính năng thay đổi số dòng hiển thị
        "searching": false, // Bật tính năng tìm kiếm
        "ordering": true, // Bật sắp xếp cột
        "info": true, // Hiển thị thông tin về số dòng
        "autoWidth": false, // Không tự động điều chỉnh chiều rộng cột
        "responsive": true, // Bật chế độ phản hồi
        "columns": [
            {"data": "Sender name"},
            {"data": "Sender Email"},
            {"data": "Receiver Email"},
            {"data": "Send Date"},
        ]
    });
});
