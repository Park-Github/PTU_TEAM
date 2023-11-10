package SpringProject.WebCommunity.Service;

import SpringProject.WebCommunity.Model.Domain.BoardArticle;
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
    public Long saveToCreate(BoardArticleCreateDto boardArticleCreateDto){
     return articleRepos.save(boardArticleCreateDto.toEntity()).getId();
    }

    @Transactional
    public BoardArticleReadDto updateTitleAndContents(Long id, BoardArticleUpdateDto boardArticleUpdateDto) {
        BoardArticle entity = articleRepos.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        entity.update(boardArticleUpdateDto.getTitle(), boardArticleUpdateDto.getContents());
        return new BoardArticleReadDto(entity);
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
    public BoardArticleReadDto findById(Long id) {
        BoardArticle entity = articleRepos.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다"));

        return new BoardArticleReadDto(entity);
    }

    @Transactional(readOnly = true)
    public Optional<BoardArticleReadDto> optionalFindById(Long id) {
        BoardArticle entity = articleRepos.findById(id).orElse(null);

        return Optional.of(new BoardArticleReadDto(entity));
    }

    @Transactional
    public void delete (Long id) {
        BoardArticle boardArticle = articleRepos.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        articleRepos.delete(boardArticle);
    }

    @Transactional(readOnly = true)
    public List<BoardArticleReadDto> findAll() {
        return articleRepos.findAll().stream()
                .map(BoardArticleReadDto::new)
                .collect(Collectors.toList());
    }

    public PageResultDto<BoardArticleReadDto, BoardArticle> getList(PageRequestDto requestDto,
                                                                    String sort, String category) {
        Pageable pageable = requestDto.getPageRequest(Sort.by(Sort.Direction.DESC, sort));
        Page<BoardArticle> result =
                articleRepos.findAllByCategory(category, pageable);
        Function<BoardArticle, BoardArticleReadDto> function = (articleRepos::entityToDto);

        return new PageResultDto<>(result, function);
    }

    public PageResultDto<BoardArticleReadDto, BoardArticle> getListByTitle(PageRequestDto requestDto,
                                                                           String sort, String category,
                                                                           String title) {
        Pageable pageable = requestDto.getPageRequest(Sort.by(Sort.Direction.DESC, sort));
        Page<BoardArticle> result =
                articleRepos.findAllByCategoryAndTitleContaining(category, title, pageable);
        Function<BoardArticle, BoardArticleReadDto> function = (articleRepos::entityToDto);

        return new PageResultDto<>(result, function);
    }

    public PageResultDto<BoardArticleReadDto, BoardArticle> getListByContents(PageRequestDto requestDto,
                                                                              String sort, String category,
                                                                              String contents) {
        Pageable pageable = requestDto.getPageRequest(Sort.by(Sort.Direction.DESC, sort));
        Page<BoardArticle> result =
                articleRepos.findAllByCategoryAndContentsContaining(category, contents, pageable);
        Function<BoardArticle, BoardArticleReadDto> function = (articleRepos::entityToDto);

        return new PageResultDto<>(result, function);
    }

    public PageResultDto<BoardArticleReadDto, BoardArticle> getListByUserName(PageRequestDto requestDto,
                                                                              String sort, String category,
                                                                              String createdBy) {
        Pageable pageable = requestDto.getPageRequest(Sort.by(Sort.Direction.DESC, sort));
        Page<BoardArticle> result =
                articleRepos.findAllByCategoryAndCreatedByContaining(category, createdBy, pageable);
        Function<BoardArticle, BoardArticleReadDto> function = (articleRepos::entityToDto);

        return new PageResultDto<>(result, function);
    }

    /// Using Query DSL

    public List<BoardArticle> find2ByCategoryDesc(String category) {
        return articleQueryRepos.find2ByCategoryDesc(category);
    }
    public List<BoardArticle> findAllByContents(String contents) {
        return articleQueryRepos.findAllByContents(contents);
    }
    public List<BoardArticle> findAllByCategory(String category) {
        return articleQueryRepos.findAllByCategory(category);
    }
    public List<BoardArticle> findAllByTimeDesc(String category) {
        return articleQueryRepos.findAllOrderByTimeDesc(category);
    }
}
