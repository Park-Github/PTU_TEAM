package SpringProject.WebCommunity.Service;

import SpringProject.WebCommunity.Model.Domain.Article;
import SpringProject.WebCommunity.Model.Dto.*;
import SpringProject.WebCommunity.Repository.ArticleQueryRepos;
import SpringProject.WebCommunity.Repository.ArticleRepos;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor // final 필드 생성자 자동 생성
@Service
public class ArticleService {
    private final ArticleRepos articleRepos;
    private final ArticleQueryRepos articleQueryRepos;

    @Transactional
    public Long saveToCreate(ArticleCreateDto articleCreateDto){
     return articleRepos.save(articleCreateDto.toEntity()).getId();
    }

    @Transactional
    public ArticleReadDto updateTitleAndContents(Long id, ArticleUpdateDto articleUpdateDto) {
        Article entity = articleRepos.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        entity.update(articleUpdateDto.getTitle(), articleUpdateDto.getContents());
        return new ArticleReadDto(entity);
    }

    @Transactional
    public void updateViewCount(Long id) {
        articleQueryRepos.updateViewCount(id);
    }

    @Transactional
    public void updateLikeCount(Long id) {
        articleQueryRepos.updateLikeCount(id);
    }

    @Transactional(readOnly = true)
    public ArticleReadDto findById(Long id) {
        Article entity = articleRepos.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다"));

        return new ArticleReadDto(entity);
    }

    @Transactional(readOnly = true)
    public Optional<ArticleReadDto> optionalFindById(Long id) {
        Article entity = articleRepos.findById(id).orElse(null);

        return Optional.of(new ArticleReadDto(entity));
    }

    @Transactional
    public void delete (Long id) {
        Article article = articleRepos.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        articleRepos.delete(article);
    }

    @Transactional(readOnly = true)
    public List<ArticleReadDto> findAll() {
        return articleRepos.findAll().stream()
                .map(ArticleReadDto::new)
                .collect(Collectors.toList());
    }

    public PageResultDto<ArticleReadDto, Article> getList(PageRequestDto requestDto,
                                                          String sort, String category) {
        Pageable pageable = requestDto.getPageRequest(Sort.by(Sort.Direction.DESC, sort));
        Page<Article> result =
                articleRepos.findAllByCategory(category, pageable);
        Function<Article, ArticleReadDto> function = (articleRepos::entityToDto);

        return new PageResultDto<>(result, function);
    }

    public PageResultDto<ArticleReadDto, Article> getListByTitle(PageRequestDto requestDto,
                                                                 String sort, String category,
                                                                 String title) {
        Pageable pageable = requestDto.getPageRequest(Sort.by(Sort.Direction.DESC, sort));
        Page<Article> result =
                articleRepos.findAllByCategoryAndTitleContaining(category, title, pageable);
        Function<Article, ArticleReadDto> function = (articleRepos::entityToDto);

        return new PageResultDto<>(result, function);
    }

    public PageResultDto<ArticleReadDto, Article> getListByContents(PageRequestDto requestDto,
                                                                    String sort, String category,
                                                                    String contents) {
        Pageable pageable = requestDto.getPageRequest(Sort.by(Sort.Direction.DESC, sort));
        Page<Article> result =
                articleRepos.findAllByCategoryAndContentsContaining(category, contents, pageable);
        Function<Article, ArticleReadDto> function = (articleRepos::entityToDto);

        return new PageResultDto<>(result, function);
    }

    public PageResultDto<ArticleReadDto, Article> getListByUserName(PageRequestDto requestDto,
                                                                    String sort, String category,
                                                                    String createdBy) {
        Pageable pageable = requestDto.getPageRequest(Sort.by(Sort.Direction.DESC, sort));
        Page<Article> result =
                articleRepos.findAllByCategoryAndCreatedByContaining(category, createdBy, pageable);
        Function<Article, ArticleReadDto> function = (articleRepos::entityToDto);

        return new PageResultDto<>(result, function);
    }

    /// Using Query DSL

    public List<Article> find2ByCategoryDesc(String category) {
        return articleQueryRepos.find2ByCategoryDesc(category);
    }
    public List<Article> find3ByCategoryDesc(String category) {
        return articleQueryRepos.find3ByCategoryDesc(category);
    }

}
