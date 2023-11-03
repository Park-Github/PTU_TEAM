package SpringProject.WebCommunity.Service;

import SpringProject.WebCommunity.Domain.BoardArticle;
import SpringProject.WebCommunity.Dto.BoardArticleCreateDto;
import SpringProject.WebCommunity.Dto.BoardArticleReadDto;
import SpringProject.WebCommunity.Dto.BoardArticleUpdateDto;
import SpringProject.WebCommunity.Repository.ArticleRepos;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor // final 필드 생성자 자동 생성
@Service
public class ArticleService {
    private final ArticleRepos articleRepos;

    @Transactional
    public Long saveToCreate(BoardArticleCreateDto boardArticleCreateDto){
     return articleRepos.save(boardArticleCreateDto.toEntity()).getId();
    }

    @Transactional
    public Long saveToUpdate(BoardArticleReadDto boardArticleReadDto){
        return articleRepos.save(boardArticleReadDto.toEntity()).getId();
    }

    @Transactional
    public BoardArticleReadDto update(Long id, BoardArticleUpdateDto boardArticleUpdateDto) {
        BoardArticle entity = articleRepos.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        entity.update(boardArticleUpdateDto.getTitle(), boardArticleUpdateDto.getContents());
        return new BoardArticleReadDto(entity);
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
}
