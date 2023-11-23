package SpringProject.WebCommunity.Model.Domain;

import SpringProject.WebCommunity.Model.Dto.AttachmentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Attachment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clientFileName;

    private String serverFileName;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    public Attachment(String clientFileName, String serverFileName, Article article) {
        this.clientFileName = clientFileName;
        this.serverFileName = serverFileName;
        this.article = article;
    }

    public static Attachment attachmentToEntity(AttachmentDto dto, Article article) {
        // 예외 처리
        if (dto.getId() != null)
            throw new IllegalArgumentException("첨부파일 업로드 경로 저장 실패! 첨부 파일 PK 제약사항 위반!");
        if (!dto.getArticleId().equals(article.getId()))
            throw new IllegalArgumentException("첨부파일 업로드 경로 저장 실패! 게시글 PK 제약사항 위반!");

        // 엔티티 생성 및 반환
        return new Attachment(
                dto.getClientFileName(),
                dto.getServerFileName(),
                article
        );
    }
}
