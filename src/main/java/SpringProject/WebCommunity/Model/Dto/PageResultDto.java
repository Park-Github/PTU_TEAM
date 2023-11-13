package SpringProject.WebCommunity.Model.Dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDto<DTO, EN> {

    private List<DTO> dtoList; // dto List

    private int totalPage; // 전체 페이지 수

    private int size; // 페이지당 목록 개수

    private int start, current, end; // 시작 페이지 번호, 마지막 페이지 번호

    private boolean prev, next; // 이전 버튼, 다음 버튼

    private List<Integer> pageList; // 페이지 번호 List
    public PageResultDto(Page<EN> result,
                         Function<EN, DTO> function) {
        dtoList = result.stream().map(function)
                .collect(Collectors.toList());
        totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable) {
        this.current = pageable.getPageNumber() + 1;// PageNumber Start 0
        this.size = pageable.getPageSize();

        int tempEnd = (int)(Math.ceil(current/10.0)) * 10;

        start = tempEnd - 9;
        prev = start > 1;
        end = Math.min(totalPage, tempEnd);
        next = totalPage > tempEnd;

        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }
}
