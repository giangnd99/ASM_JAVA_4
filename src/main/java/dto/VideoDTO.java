package dto;

import entity.Video;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import service.HistoryService;
import service.impl.HistoryServiceImpl;

@Getter
@Setter
public class VideoDTO {

    private Integer id;

    private String title;

    private String href;

    private String poster;

    private Integer views;

    private Integer likes;

    private Integer shares;

    private String description;

    private boolean isActive;

    public static VideoDTO fromEntity(Video video) {
        VideoDTO dto = new VideoDTO();
        dto.setId(video.getId());
        dto.setTitle(video.getTitle());
        dto.setDescription(video.getDescription());
        dto.setHref(video.getHref());
        dto.setViews(video.getViews());
        dto.setActive(video.isActive());
        return dto;
    }

}
