package AELE.backend.cctv.DTO;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Data
public class PageDTO {

    private int page = 0;

    private int size = 10;

    private String sortDirection = "DESC"; // 기본값은 최신순으로 정렬

    private String criteria = "createdAt";

    public static PageRequest toPageRequest(PageDTO pageDTO) {
        Sort sort = Sort.by(
                (pageDTO.getSortDirection().equals("ASC")) ? Sort.Direction.ASC : Sort.Direction.DESC,
                pageDTO.getCriteria()
        );
        return PageRequest.of(pageDTO.getPage(), pageDTO.getSize(), sort);
    }

}
