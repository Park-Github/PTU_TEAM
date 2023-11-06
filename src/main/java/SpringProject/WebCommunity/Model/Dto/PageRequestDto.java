package SpringProject.WebCommunity.Model.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Data
@Builder
@AllArgsConstructor
public class PageRequestDto {
    private int page;
    private int size;

    public PageRequestDto() {
        this.page = 1;
        this.size = 10;
    }

    public PageRequest getPageRequest(Sort sort) {
        return PageRequest.of(page -1, size, sort);
    }
}
