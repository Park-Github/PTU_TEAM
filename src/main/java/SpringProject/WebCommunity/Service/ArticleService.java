package SpringProject.WebCommunity.Service;

import SpringProject.WebCommunity.Domain.Article;
import SpringProject.WebCommunity.Dto.ArticleCreateDto;
import SpringProject.WebCommunity.Dto.ArticleReadDto;
import SpringProject.WebCommunity.Dto.ArticleUpdateDto;
import SpringProject.WebCommunity.Repository.ArticleRepos;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor // final 필드 생성자 자동 생성
@Service
public class ArticleService {
    private final ArticleRepos articleRepos;

    @Transactional
    public Long save(ArticleCreateDto articleCreateDto){
     return articleRepos.save(articleCreateDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, ArticleUpdateDto articleUpdateDto) {
        Article article = articleRepos.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));
        article.update(articleUpdateDto.getTitle(), articleUpdateDto.getContents());
        return id;
    }

    @Transactional(readOnly = true)
    public ArticleReadDto findById(Long id) {
        Article entity = articleRepos.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        return new ArticleReadDto(entity);
    }

    @Transactional
    public void delete (Long id) {
        Article article = articleRepos.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        articleRepos.delete(article);
    }
}
