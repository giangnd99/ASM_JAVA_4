class VideoAPI {
    constructor() {
        this.apiUrl = "api/videos";
    }

    async getAllVideos() {
        const response = await fetch(this.apiUrl);
        if (!response.ok) throw new Error("Failed to fetch videos");
        return response.json();
    }

    async createVideo(video) {
        const response = await fetch(this.apiUrl, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(video),
        });
        if (!response.ok) throw new Error("Failed to create video");
    }

    async updateVideo(video) {
        const response = await fetch(`${this.apiUrl}/${video.id}`, {
            method: "PUT",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(video),
        });
        if (!response.ok) throw new Error("Failed to update video");
    }

    async deleteVideo(id) {
        const response = await fetch(`${this.apiUrl}/${id}`, {method: "DELETE"});
        if (!response.ok) throw new Error("Failed to delete video");
    }
}

document.addEventListener("DOMContentLoaded", () => {
    const videoAPI = new VideoAPI();
    const DOM = {
        videoTableBody: document.querySelector("#videoTable tbody"),
        createBtn: document.getElementById("createBtn"),
        updateBtn: document.getElementById("updateBtn"),
        deleteBtn: document.getElementById("deleteBtn"),
        resetBtn: document.getElementById("resetBtn"),
        paginationContainer: document.querySelector(".pagination"),
        posterPreview: document.getElementById("posterPreview"),
        videoForm: document.getElementById("videoForm"),
        posterHrefInput: document.getElementById("poster"),
    };

    let videos = [];
    let currentPage = 1;
    const pageSize = 5;
    let currentVideo = null;

    const loadVideos = async () => {
        try {
            videos = await videoAPI.getAllVideos();
            displayVideos();
            updatePagination();
        } catch (error) {
            alert("Failed to load videos.");
            console.error(error);
        }
    };

    const displayVideos = () => {
        const start = (currentPage - 1) * pageSize;
        const videosToDisplay = videos.slice(start, start + pageSize);

        DOM.videoTableBody.innerHTML = videosToDisplay
            .map(video => `
                <tr>
                    <td>${video.id}</td>
                    <td>${video.title}</td>
                    <td><img src="${video.poster}" alt="Poster" style="width:100px;"></td>
                    <td>${video.views}</td>
                    <td>${video.description}</td>
                    <td>${video.active ? "Active" : "Inactive"}</td>
                    <td>
                        <button class="btn btn-sm btn-primary" data-id="${video.id}">Edit</button>
                    </td>
                </tr>
            `).join("");

        document.querySelectorAll(".btn-primary").forEach(button => {
            button.addEventListener("click", () => editVideo(parseInt(button.dataset.id, 10)));
        });
    };

    const updatePagination = () => {
        const totalPages = Math.ceil(videos.length / pageSize);
        DOM.paginationContainer.innerHTML = `
            <li class="page-item ${currentPage === 1 ? 'disabled' : ''}">
                <a class="page-link" href="#" id="prevPage">Previous</a>
            </li>
            ${Array.from({length: totalPages}, (_, i) => `
                <li class="page-item ${currentPage === i + 1 ? 'active' : ''}">
                    <a class="page-link" href="#" data-page="${i + 1}">${i + 1}</a>
                </li>
            `).join('')}
            <li class="page-item ${currentPage === totalPages ? 'disabled' : ''}">
                <a class="page-link" href="#" id="nextPage">Next</a>
            </li>
        `;

        document.querySelectorAll(".page-link[data-page]").forEach(link => {
            link.addEventListener("click", e => {
                e.preventDefault();
                currentPage = parseInt(link.dataset.page, 10);
                displayVideos();
                updatePagination();
            });
        });

        document.getElementById("prevPage")?.addEventListener("click", e => {
            e.preventDefault();
            if (currentPage > 1) currentPage--;
            displayVideos();
            updatePagination();
        });

        document.getElementById("nextPage")?.addEventListener("click", e => {
            e.preventDefault();
            if (currentPage < totalPages) currentPage++;
            displayVideos();
            updatePagination();
        });
    };

    const editVideo = id => {
        // Tìm video cần chỉnh sửa
        currentVideo = videos.find(v => v.id === id);
        if (!currentVideo) return;

        // Đồng bộ dữ liệu từ video vào form
        ["id", "href", "title", "views", "description", "poster"].forEach(field => {
            const input = document.getElementById(field);
            if (input) input.value = currentVideo[field];
        });

        // Cập nhật trạng thái (radio)
        document.querySelector(`#status${currentVideo.active ? "Active" : "Inactive"}`).checked = true;

        // Ẩn/Hiện nút hành động
        DOM.createBtn.hidden = true;
        DOM.updateBtn.hidden = false;
        DOM.deleteBtn.hidden = false;

        // Chuyển sang tab chỉnh sửa
        const editTab = new bootstrap.Tab(document.getElementById("edit-tab"));
        editTab.show();

        updatePosterPreview(currentVideo.href);
    };

    const resetForm = () => {
        DOM.videoForm.reset();
        DOM.posterPreview.src = "https://via.placeholder.com/150";
        currentVideo = null;

        DOM.createBtn.hidden = false;
        DOM.updateBtn.hidden = true;
        DOM.deleteBtn.hidden = true;
    };

    const updatePosterPreview = href => {
        DOM.posterPreview.src = href ? `https://img.youtube.com/vi/${href}/maxresdefault.jpg` : "https://via.placeholder.com/150";
    };

    DOM.posterHrefInput.addEventListener("change", (e) => {
        updatePosterPreview(e.target.value);
    });

    DOM.createBtn.addEventListener("click", async () => {
        try {
            const video = {
                href: document.getElementById("href").value,
                title: document.getElementById("title").value,
                views: parseInt(document.getElementById("views").value, 10),
                active: document.querySelector('input[name="active"]:checked').value === "true",
                description: document.getElementById("description").value,
                poster: DOM.posterPreview.src,
            };
            await videoAPI.createVideo(video);
            alert("successfully")
            loadVideos();
        } catch (error) {
            alert("Failed to create video.");
            console.error(error);
        }
    });

    DOM.updateBtn.addEventListener("click", async () => {
        try {
            if (!currentVideo) return;

            currentVideo = {
                ...currentVideo,
                href: document.getElementById("href").value,
                title: document.getElementById("title").value,
                views: parseInt(document.getElementById("views").value, 10),
                active: document.querySelector('input[name="active"]:checked').value === "true",
                description: document.getElementById("description").value,
                poster: DOM.posterPreview.src,
            };

            await videoAPI.updateVideo(currentVideo);
            alert("successfully")
            loadVideos();
        } catch (error) {
            alert("Failed to update video.");
            console.error(error);
        }
    });

    DOM.deleteBtn.addEventListener("click", async () => {
        try {
            if (!currentVideo) return;
            await videoAPI.deleteVideo(currentVideo.id);
            alert("Success: The video will no longer appear on the Home page.")
            loadVideos();
        } catch (error) {
            alert("Failed to delete video.");
            console.error(error);
        }
    });

    DOM.resetBtn.addEventListener("click", resetForm);

    loadVideos();
});
