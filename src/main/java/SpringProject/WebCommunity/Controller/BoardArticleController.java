package SpringProject.WebCommunity.Controller;

import SpringProject.WebCommunity.Model.Domain.Article;
import SpringProject.WebCommunity.Model.Domain.Member;
import SpringProject.WebCommunity.Model.Dto.*;
import SpringProject.WebCommunity.Service.ArticleService;
import SpringProject.WebCommunity.Service.AttachmentService;
import SpringProject.WebCommunity.Service.CommentService;
import SpringProject.WebCommunity.Service.MemberService;
import com.querydsl.core.Tuple;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static SpringProject.WebCommunity.Controller.CommonController.UpdateAndRegisterModel;


@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardArticleController {

    private final ArticleService articleService;
    private final MemberService memberService;
    private final CommentService commentService;
    private final AttachmentService attachmentService;

    // 게시판 글쓰기 페이지 요청 처리
    @GetMapping("/board/form/{cat}")
    public String newArticleForm(@PathVariable(name = "cat") String category,
                                 Model model,
                                 ArticleCreateDto dto) {
        model.addAttribute("boardArticle", dto);
        model.addAttribute("category", category);
        return "form/post-write";
    }

    // 게시판 게시글 입력 데이터 POST mapping
    @SneakyThrows
    @PostMapping("/board/create")
    public String createBoardArticle(ArticleCreateDto form,
                                     @RequestParam List<MultipartFile> multipartFile,
                                     HttpServletRequest request,
                                     RedirectAttributes redirectAttr) {

        Optional<Member> member = memberService.getMember(request);
        if (member.isPresent()) {
            Member user = member.get();
            ArticleCreateDto dto = new ArticleCreateDto(form.getTitle(), form.getContents(), form.getCategory(), user);
            Long boardId = articleService.save(dto);

            if (!multipartFile.get(0).isEmpty()){
                List<Long> attachmentIdList = attachmentService.save(boardId, multipartFile);
                Map<Long, String> fileMap = attachmentService.mappingFileName(attachmentIdList);
                redirectAttr.addFlashAttribute("fileMap", fileMap);
            }

            redirectAttr.addFlashAttribute("success", "게시글이 등록되었습니다.");
            log.info(boardId.toString());
            return "redirect:/board/view/" + boardId;
        }
        else
            return "redirect:/home";
    }

    // 게시판 게시글 조회
    @GetMapping("/board/view/{id}") // TODO: 2023-11-03 articleReadDto null 예외처리
    public String showArticle(@PathVariable Long id,
                              HttpServletRequest request,
                              Model model) {
        Optional<Article> boardArticle = Optional.ofNullable(articleService.findById(id).toEntity());
        Optional<Member> member = memberService.getMember(request);
        List<CommentDto> commentDtoList = commentService.findComments(id);
        Map<Long, String> fileMap = attachmentService.readFileMap(id);
        log.info(fileMap.toString());

        member.ifPresent(value -> model.addAttribute("member", value));
        boardArticle.ifPresent(value -> {
            model.addAttribute("boardArticle", value);
            model.addAttribute("commentList", commentDtoList);
            model.addAttribute("fileMap", fileMap);
            articleService.updateViewCount(id);
        });
        return "menu/article";
    }

    // 게시판 게시글 목록 조회
    @GetMapping("/board/view") // TODO: 2023/11/10 현재 URL 경로 model 등록
    public String articleListView(@RequestParam(name = "category") String category,
                                  @RequestParam(name = "sort", defaultValue = "createdTime") String sort,
                                  PageRequestDto pageRequestDto,
                                  Model model) {
        log.info(sort);
        PageResultDto<ArticleReadDto, Article> resultDto
                = articleService.getList(pageRequestDto, sort, category);

        model.addAttribute("boardArticleList", resultDto);

        model.addAttribute("boardCat", category);

        model.addAttribute("sort", sort);
        return "menu/article-list";

    }

    // 게시판 게시글 수정 페이지 요청 처리
    @GetMapping("/board/edit") // TODO: 2023-11-03 findById nullPointException 예외처리
    public String articleEditPageView(@RequestParam(name = "category") String category,
                                      @RequestParam(name = "id") Long id,
                                      Model model) {
       UpdateAndRegisterModel(category, id, model, articleService);
       return "form/post-edit";
    }

    // 게시판 게시글 수정 데이터 POST mapping
    @PostMapping ("/board/edit")
    public String editBoardArticle(@RequestParam(name = "id") Long id,
                                   ArticleUpdateDto form,
                                   Model model) {

        ArticleReadDto article = articleService.updateTitleAndContents(id, form);
        model.addAttribute("boardArticle", article);

        return "redirect:/board/view/" + id;
    }

    // 게시판 게시글 삭제 요청
    @GetMapping("/board/delete/{category}") // TODO: 2023-11-02 게시글 삭제 요청시 삭제글 없을 시 예외 처리
    public String articleDeletePageView(@RequestParam(name = "id") Long id,
                                        @PathVariable(name = "category") String category,
                                        RedirectAttributes redirectAttr) {
        Optional<ArticleReadDto> dto = articleService.optionalFindById(id);

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

        CommonController.registerPageModels(category, condition, keyWord,
                sort, pageRequestDto, model, articleService, log);


        return "menu/article-list";
    }

    // 게시글 공감 버튼 요청
    @GetMapping("/board/like/{id}")
    public String clickLikes(@PathVariable(name = "id") Long id,
                             Model model) {
        Optional<Article> boardArticle;
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

