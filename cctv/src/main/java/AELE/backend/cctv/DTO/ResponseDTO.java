package AELE.backend.cctv.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ResponseDTO {

    private String status; // "success", "error"만
    private String message; // 에러 메세지
    private Object data;
    private int size;

    static public ResponseDTO toDTO(String status, String message) {
        // 에러 처리용
        return ResponseDTO.builder()
                .status(status)
                .message(message)
                .build();
    }

    static public ResponseDTO toDTO(List<VideoDTO> list) {
        return ResponseDTO.builder()
                .status("success")
                .data(list)
                .size(list.size())
                .build();
    }

    static public ResponseDTO toDTO(VideoDetailDTO detail) {
        return ResponseDTO.builder()
                .status("success")
                .data(detail)
                .size(1)
                .build();
    }

}
