package dto;

import entity.Video;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import service.HistoryService;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoryDTO {
    private VideoDTO video;
    private String likeDateLatest;
    private String likeDateOldest;

    private List<FavoriteDTO> favorites;
    private List<ShareDTO> shares;

    public static List<HistoryDTO> getAllVideosMapper(List<Video> videos, HistoryService historyService) {
        return videos.stream().map(video -> {
            HistoryDTO dto = new HistoryDTO();

            // Map Video entity to VideoDTO
            VideoDTO videoDTO = VideoDTO.fromEntity(video);

            // Fetch like/share counts and set to VideoDTO
            videoDTO.setLikes(historyService.getLikeCount(video.getId()));
            videoDTO.setShares(historyService.getShareCount(video.getId()));

            // Set basic information to HistoryDTO
            dto.setVideo(videoDTO);
            dto.setLikeDateLatest(historyService.getLatestLikeDate(video.getId()));
            dto.setLikeDateOldest(historyService.getOldestLikeDate(video.getId()));

            // Fetch and map Favorites and Shares
            dto.setFavorites(historyService.getFavoritesByHref(video.getHref()).stream()
                    .map(FavoriteDTO::fromEntity)
                    .collect(Collectors.toList()));
            dto.setShares(historyService.getSharesByHref(video.getHref()).stream()
                    .map(ShareDTO::fromEntity)
                    .collect(Collectors.toList()));

            return dto;
        }).collect(Collectors.toList());
    }
}
