package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VideoHistoryDTO {
    // Common fields
    private String videoTitle;
    private String videoHref;
    private String description;
    private String viewCount;
    private String isActive;
    // For Favorites
    private int likeCount;
    private String likeDateLatest;
    private String likeDateOldest;

    // For Favorite Users
    private String username;
    private String fullname;
    private String email;
    private String favoriteDate;

    // For Shared Friends
    private int shareCount;
    private String senderName;
    private String senderEmail;
    private String receiverEmail;
    private String shareDate;
    private String shareDateOldest;
    private String shareDateLatest;

    public boolean isFavoriteSection() {
        return likeCount > 0;
    }

    public boolean isFavoriteUserSection() {
        return username != null && fullname != null;
    }

    public boolean isSharedFriendSection() {
        return senderName != null && receiverEmail != null;
    }
}
