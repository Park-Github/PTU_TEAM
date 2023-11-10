package SpringProject.WebCommunity.Controller;

import SpringProject.WebCommunity.Model.Domain.BoardArticle;
import SpringProject.WebCommunity.Model.Domain.Member;
import SpringProject.WebCommunity.Model.Dto.*;
import SpringProject.WebCommunity.Service.ArticleService;
import SpringProject.WebCommunity.Service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardArticleController {

    private final ArticleService articleService;
    private final MemberService memberService;

    // 게시판 글쓰기 페이지 요청 처리
    @GetMapping("/board/form/{cat}")
    public String newArticleForm(@PathVariable(name = "cat") String category,
                                 Model model, BoardArticleCreateDto dto) {
        model.addAttribute("boardArticle", dto);
        model.addAttribute("category", category);
        return "/form/post-write";
    }

    // 게시판 게시글 입력 데이터 POST mapping
    @PostMapping("/board/create")
    public String createBoardArticle(BoardArticleCreateDto form,
                                     HttpServletRequest request,
                                     RedirectAttributes redirectAttr) {

        Optional<Member> member = memberService.getMember(request);
        if (member.isPresent()) {
            Long boardId = articleService.saveToCreate(form);
            redirectAttr.addFlashAttribute("success", "게시글이 등록되었습니다.");
            log.info(boardId.toString());
            return "redirect:/board/view/" + boardId;
        }
        else
            return "redirect:/home";
    }

    // 게시판 게시글 조회
    @GetMapping("/board/view/{id}") // TODO: 2023-11-03 articleReadDto null 예외처리
    public String showArticle(@PathVariable Long id, Model model) {
        log.info("id = " + id);
        Optional<BoardArticle> boardArticle = Optional.ofNullable(articleService.findById(id).toEntity());

        if (boardArticle.isPresent()){
            model.addAttribute("boardArticle", boardArticle.get());
            articleService.updateViewCount(id);
            return "/menu/article";
        }
        else {
            return "redirect:/home";
        }
    }

    // 게시판 게시글 목록 조회
    @GetMapping("/board/view") // TODO: 2023/11/10 현재 URL 경로 model 등록
    public String articleListView(@RequestParam(name = "category") String category,
                                  @RequestParam(name = "sort", defaultValue = "createdTime") String sort,
                                  PageRequestDto pageRequestDto,
                                  Model model) {
        log.info(sort);
        PageResultDto<BoardArticleReadDto, BoardArticle> resultDto
                = articleService.getList(pageRequestDto, sort, category);

        model.addAttribute("boardArticleList", resultDto);

        model.addAttribute("boardCat", category);

        model.addAttribute("sort", sort);
        return "/menu/article-list";

    }

    // 게시판 게시글 수정 페이지 요청 처리
    @GetMapping("/board/edit") // TODO: 2023-11-03 findById nullPointException 예외처리
    public String articleEditPageView(@RequestParam(name = "category") String category,
                                      @RequestParam(name = "id") Long id,
                                      Model model) {
        BoardArticleReadDto readDto = articleService.findById(id);
        BoardArticleUpdateDto updateDto = new BoardArticleUpdateDto(id, readDto.getTitle(), readDto.getContents());
        log.info(String.valueOf(id));
        log.info(updateDto.getTitle());
        log.info(updateDto.getContents());

        model.addAttribute("cat", category);
        model.addAttribute("articleDto", updateDto);
        model.addAttribute("id", id);

        return "/form/post-edit";
    }

    // 게시판 게시글 수정 데이터 POST mapping
    @PostMapping ("/board/edit")
    public String editBoardArticle(@RequestParam(name = "id") Long id,
                                   BoardArticleUpdateDto form,
                                   Model model) {
        log.info(form.getTitle() + "제목");
        log.info(form.getContents() + "내용");
        log.info(id.toString() + "id");

        BoardArticleReadDto article = articleService.updateTitleAndContents(id, form);
        model.addAttribute("boardArticle", article);

        return "redirect:/board/view/" + id;
    }

    // 게시판 게시글 삭제 요청
    @GetMapping("/board/delete/{category}") // TODO: 2023-11-02 게시글 삭제 요청시 삭제글 없을 시 예외 처리
    public String articleDeletePageView(@RequestParam(name = "id") Long id,
                                        @PathVariable(name = "category") String category,
                                        RedirectAttributes redirectAttr) {
        Optional<BoardArticleReadDto> dto = articleService.optionalFindById(id);

        if (dto.isPresent()) {
            articleService.delete(id);
            redirectAttr.addFlashAttribute("success", "게시글이 삭제되었습니다.");
            return switch (category) {
                default -> "redirect:/board/view?category=free";
                case "sharing" -> "redirect:/board/view?category=sharing";
                case "qna" -> "redirect:/board/view?category=qna";
                case "promotion" -> "redirect:/board/view?category=promotion";
            };
        }
        else {
            redirectAttr.addFlashAttribute("fail", "게시글을 찾을 수 없어 삭제에 실패하였습니다..");
            return switch (category) {
                default -> "redirect:/board/view?category=free";
                case "sharing" -> "redirect:/board/view?category=sharing";
                case "qna" -> "redirect:/board/view?category=qna";
                case "promotion" -> "redirect:/board/view?category=promotion";
            };
        }
    }

    @PostMapping("/board/search")
    public String articleSearch(@RequestParam(name = "category") String category,
                                @RequestParam(name = "condition", defaultValue = "") String condition,
                                @RequestParam(name = "keyWord", defaultValue = "") String keyWord,
                                @RequestParam(name = "sort") String sort,
                                PageRequestDto pageRequestDto,
                                Model model) {

        log.info("-----PostMapping Start-----");
        log.info(condition);
        switch (condition) {
            case "title":
                log.info(category);
                PageResultDto<BoardArticleReadDto, BoardArticle> resultDto1
                        = articleService.getListByTitle(pageRequestDto, sort, category, keyWord);
                model.addAttribute("boardArticleList", resultDto1);
                break;
            case "contents":
                PageResultDto<BoardArticleReadDto, BoardArticle> resultDto2
                        = articleService.getListByContents(pageRequestDto, sort, category, keyWord);
                model.addAttribute("boardArticleList", resultDto2);
                break;
            case "createdBy":
                PageResultDto<BoardArticleReadDto, BoardArticle> resultDto3
                        = articleService.getListByUserName(pageRequestDto, sort, category, keyWord);
                model.addAttribute("boardArticleList", resultDto3);
                break;
            default:
                PageResultDto<BoardArticleReadDto, BoardArticle> resultDto4
                        = articleService.getList(pageRequestDto, sort, category);
                model.addAttribute("boardArticleList", resultDto4);
                break;
        }
        model.addAttribute("boardCat", category);
        model.addAttribute("sort", sort);
        log.info(keyWord);
        log.info("-----PostMapping End-----");

        return "menu/article-list";
    }

    // 게시글 공감 버튼 요청
    @GetMapping("/board/like/{id}")
    public String clickLikes(@PathVariable(name = "id") Long id,
                             Model model) {
        Optional<BoardArticle> boardArticle;
        boardArticle = Optional.ofNullable(articleService.findById(id).toEntity());

        if (boardArticle.isPresent()){
            model.addAttribute("boardArticle", boardArticle.get());
            articleService.updateLikeCount(id);
            return "redirect:/board/view/" + id;
        }
        else {
            return "redirect:/home";
        }
    }

}

