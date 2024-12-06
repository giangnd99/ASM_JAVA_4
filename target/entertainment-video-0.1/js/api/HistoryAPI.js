document.addEventListener("DOMContentLoaded", () => {
    const API_URL = "api/history";
    const videoFilter = document.getElementById("videoFilter");
    const favoritesTableBody = document.getElementById("favoritesTableBody");
    const favoriteUsersTableBody = document.getElementById("favoriteUsersTableBody");
    const sharedFriendsTableBody = document.getElementById("sharedFriendsTableBody");
    let data = [];
    async function fetchData() {
        try {
            const response = await fetch(API_URL);
            if (!response.ok) throw new Error("Failed to fetch data from API");
            data = await response.json();
            populateFilter();
            updateFavorites();
        } catch (error) {
            console.error("Error fetching data:", error);
        }
    }

    function populateFilter() {
        videoFilter.innerHTML = '<option value="all" selected>All Videos</option>'; // Reset filter

        data.forEach(item => {
            const option = document.createElement("option");
            option.value = item.video.id;
            option.textContent = item.video.title;
            videoFilter.appendChild(option);
        });
    }
    function updateFavorites() {
        const filteredData = videoFilter.value === "all"
            ? data
            : data.filter(item => item.video.id == videoFilter.value);

        favoritesTableBody.innerHTML = "";
        filteredData.forEach(item => {
            favoritesTableBody.innerHTML += `
        <tr>
          <td>${item.video.title}</td>
          <td>${item.favorites.length}</td>
          <td>${item.likeDateLatest}</td>
          <td>${item.likeDateOldest}</td>
        </tr>`;
        });
    }
    function updateFavoriteUsers() {
        const filteredData = videoFilter.value === "all"
            ? data.flatMap(item => item.favorites.map(fav => ({...fav, title: item.video.title})))
            : data
                .filter(item => item.video.id == videoFilter.value)
                .flatMap(item => item.favorites.map(fav => ({...fav, title: item.video.title})));

        favoriteUsersTableBody.innerHTML = "";
        filteredData.forEach(fav => {
            favoriteUsersTableBody.innerHTML += `
        <tr>
          <td>${fav.fullname || "N/A"}</td>
          <td>${fav.username || "N/A"}</td>
          <td>${fav.email}</td>
          <td>${fav.favoriteDate}</td>
        </tr>`;
        });
    }

    function updateSharedFriends() {
        const filteredData = videoFilter.value === "all"
            ? data.flatMap(item => item.shares.map(share => ({...share, title: item.video.title})))
            : data
                .filter(item => item.video.id == videoFilter.value)
                .flatMap(item => item.shares.map(share => ({...share, title: item.video.title})));

        sharedFriendsTableBody.innerHTML = "";
        filteredData.forEach(share => {
            sharedFriendsTableBody.innerHTML += `
        <tr>
          <td>${share.senderName || "N/A"}</td>
          <td>${share.senderEmail || "N/A"}</td>
          <td>${share.receiverEmail || "N/A"}</td>
          <td>${share.shareDate || "N/A"}</td>
        </tr>`;
        });
    }

    videoFilter.addEventListener("change", () => {
        updateFavorites();
        updateFavoriteUsers();
        updateSharedFriends();
    });
    fetchData();
});
