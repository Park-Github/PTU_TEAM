package SpringProject.WebCommunity.Model.Dto;

import SpringProject.WebCommunity.Model.Domain.Article;
import SpringProject.WebCommunity.Model.Domain.Member;
import lombok.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@Getter
@ToString // 데이터를 확인하기 위한 메서드 toString 추가
public class ArticleCreateDto {
    private final String title;
    private final String contents;
    private final String category;
    private final Member member;

    public ArticleCreateDto(String title, String contents, String category, Member member) {
        this.title = title;
        this.contents = contents;
        this.category = category;
        this.member = member;
    }

    public Article toEntity() {
        return Article.builder()
                .title(getTitle())
                .contents(getContents())
                .category(getCategory())
                .member(getMember())
                .build();
    }

}
