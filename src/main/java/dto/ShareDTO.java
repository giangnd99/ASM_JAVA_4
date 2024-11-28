package dto;

import entity.Share;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShareDTO {
    private String senderName;
    private String senderEmail;
    private String receiverEmail;
    private String shareDate;

    public static ShareDTO fromEntity(Share share) {
        ShareDTO dto = new ShareDTO();
        dto.setSenderName(share.getUserId().getFullname());
        dto.setSenderEmail(share.getUserId().getEmail());
        dto.setReceiverEmail(share.getEmails());
        dto.setShareDate(share.getShareDate().toString());
        return dto;
    }
}
