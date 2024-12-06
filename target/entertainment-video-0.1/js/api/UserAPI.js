class UserAPI {
    constructor() {
        this.apiUrl = "api/users";
    }

    // Fetch all users with pagination
    async getAllUsers() {
        const response = await fetch(this.apiUrl);
        if (!response.ok) throw new Error("Failed to fetch users.");
        return await response.json();
    }

    // Get user by ID
    async getUserById(id) {
        const response = await fetch(`${this.apiUrl}/${id}`);
        if (!response.ok) throw new Error("Failed to fetch user.");
        return await response.json();
    }

    // Create a new user
    async createUser(user) {
        const response = await fetch(this.apiUrl, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(user)
        });
        if (!response.ok) throw new Error("Failed to create user.");
        return await response.json();
    }

    // Update an existing user
    async updateUser(user) {
        const response = await fetch(`${this.apiUrl}/${user.id}`, {
            method: "PUT",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(user)
        });
        if (!response.ok) throw new Error("Failed to update user.");
        return await response.json();
    }

    // Delete user by ID
    async deleteUser(id) {
        const response = await fetch(`${this.apiUrl}/${id}`, {method: "DELETE"});
        if (!response.ok) throw new Error("Failed to delete user.");
        return await response.json();
    }
}

document.addEventListener("DOMContentLoaded", () => {
    const userAPI = new UserAPI();

    const userTableBody = document.querySelector("#userTable tbody");
    const userCount = document.getElementById("userCount");
    const saveBtn = document.getElementById("saveBtn");
    const updateBtn = document.getElementById("updateBtn");
    const deleteBtn = document.getElementById("deleteBtn");
    const resetBtn = document.getElementById("resetBtn");
    const paginationContainer = document.querySelector(".pagination");

    let users = [];
    let currentPage = 1;
    const pageSize = 5;

    const loadUsers = async () => {
        try {
            users = await userAPI.getAllUsers();
            userCount.textContent = `${users.length} users`;
            displayUsers(currentPage, pageSize);
            updatePagination();
        } catch (err) {
            console.error(err);
            alert("Failed to load users.");
        }
    };

    const displayUsers = (page, pageSize) => {
        const start = (page - 1) * pageSize;
        const end = start + pageSize;
        const usersToDisplay = users.slice(start, end);

        userTableBody.innerHTML = usersToDisplay.map(user => `
            <tr>
                <td>${user.username}</td>
                <td>${user.password}</td>
                <td>${user.fullname}</td>
                <td>${user.email}</td>
                <td>${user.admin ? "admin" : "user"}</td>
                <td>${user.active ? "active" : "off"}</td>
                <td>
                    <button class="btn btn-sm btn-primary" onclick="editUser(${user.id})">Edit</button>
                </td>
            </tr>
        `).join("");
    };

    const updatePagination = () => {
        const totalPages = Math.ceil(users.length / pageSize);
        const prevPage = document.getElementById("prev-page");
        const nextPage = document.getElementById("next-page");
        const prevPageEllipsis = document.getElementById("prev-page-ellipsis");
        const nextPageEllipsis = document.getElementById("next-page-ellipsis");

        // Disable or enable buttons based on current page
        prevPage.classList.toggle("disabled", currentPage === 1);
        nextPage.classList.toggle("disabled", currentPage === totalPages);
        prevPageEllipsis.classList.toggle("disabled", currentPage === 1);
        nextPageEllipsis.classList.toggle("disabled", currentPage === totalPages);

        prevPage.addEventListener("click", () => {
            if (currentPage > 1) {
                currentPage--;
                displayUsers(currentPage, pageSize);
                updatePagination();
            }
        });

        nextPage.addEventListener("click", () => {
            if (currentPage < totalPages) {
                currentPage++;
                displayUsers(currentPage, pageSize);
                updatePagination();
            }
        });

        prevPageEllipsis.addEventListener("click", () => {
            if (currentPage > 1) {
                currentPage--;
                displayUsers(currentPage, pageSize);
                updatePagination();
            }
        });

        nextPageEllipsis.addEventListener("click", () => {
            if (currentPage < totalPages) {
                currentPage++;
                displayUsers(currentPage, pageSize);
                updatePagination();
            }
        });
    };

    window.editUser = async (id) => {
        try {
            const user = await userAPI.getUserById(id);
            document.getElementById("id").value = user.id;
            document.getElementById("username").value = user.username;
            document.getElementById("password").value = user.password;
            document.getElementById("password").readOnly = true;
            document.getElementById("fullname").value = user.fullname;
            document.getElementById("email").value = user.email;
            document.getElementById("role").value = user.admin;
            document.getElementById("active-true").checked = (user.active === true);
            document.getElementById("active-false").checked = (user.active === false);

            saveBtn.hidden = true;
            updateBtn.hidden = false;
            deleteBtn.hidden = false;

            const userEditionTab = new bootstrap.Tab(document.getElementById('user-edition-tab'));
            userEditionTab.show();
        } catch (err) {
            console.error(err);
            alert("Failed to fetch user data.");
        }
    };

    saveBtn.addEventListener("click", async () => {
        try {
            const newUser = {
                username: document.getElementById("username").value,
                password: document.getElementById("password").value,
                fullname: document.getElementById("fullname").value,
                email: document.getElementById("email").value,
                admin: document.getElementById("role").value,
                active: document.querySelector('input[name="active"]:checked').value
            };
            await userAPI.createUser(newUser);
            alert("User created successfully!");
            loadUsers(currentPage);
        } catch (err) {
            console.error(err);
            alert("Failed to create user.");
        }
    });

    updateBtn.addEventListener("click", async () => {
        try {
            const updatedUser = {
                id: document.getElementById("id").value,
                username: document.getElementById("username").value,
                password: document.getElementById("password").value,
                fullname: document.getElementById("fullname").value,
                email: document.getElementById("email").value,
                admin: document.getElementById("role").value,
                active: document.querySelector('input[name="active"]:checked').value
            };
            await userAPI.updateUser(updatedUser);
            alert("User updated successfully!");
            loadUsers(currentPage);
        } catch (err) {
            console.error(err);
            alert("Failed to update user.");
        }
    });

    deleteBtn.addEventListener("click", async () => {
        try {
            const id = document.getElementById("id").value;
            await userAPI.deleteUser(id);
            alert("User deleted successfully!");
            loadUsers(currentPage);
        } catch (err) {
            console.error(err);
            alert("Failed to delete user.");
        }
    });

    const resetFields = () => {
        document.getElementById("username").value = "";
        document.getElementById("password").value = "";
        document.getElementById("fullname").value = "";
        document.getElementById("email").value = "";
        document.getElementById("role").value = "false";
        document.getElementById("password").readOnly = false;
        document.getElementById("active-true").checked = false;
        document.getElementById("active-false").checked = false;

        saveBtn.hidden = false;
        updateBtn.hidden = true;
        deleteBtn.hidden = true;
    };

    resetBtn.addEventListener("click", () => {
        currentPage = 1;  // Reset về trang đầu
        displayUsers(currentPage, pageSize);
        updatePagination();
    });
    resetBtn.addEventListener("click", resetFields);
    loadUsers(currentPage);
});
