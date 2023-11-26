package SpringProject.WebCommunity.Model.Dto;

import SpringProject.WebCommunity.Model.Domain.Attachment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentDto {
    private Long id;
    private String clientFileName;
    private String ServerFileName;
    private Long articleId;

    public static AttachmentDto entityToAttachmentDto(Attachment attachment) {
        return new AttachmentDto(
                attachment.getId(),
                attachment.getClientFileName(),
                attachment.getServerFileName(),
                attachment.getArticle().getId()
        );
    }
}
