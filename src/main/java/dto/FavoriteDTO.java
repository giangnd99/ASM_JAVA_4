package dto;

import entity.Favorite;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteDTO {
    private String username;
    private String fullname;
    private String email;
    private String favoriteDate;

    public static FavoriteDTO fromEntity(Favorite favorite) {
        FavoriteDTO dto = new FavoriteDTO();
        dto.setUsername(favorite.getUserId().getUsername());
        dto.setFullname(favorite.getUserId().getFullname());
        dto.setEmail(favorite.getUserId().getEmail());
        dto.setFavoriteDate(favorite.getLikedDate().toString());
        return dto;
    }
}
