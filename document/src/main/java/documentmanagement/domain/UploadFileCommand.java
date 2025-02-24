package documentmanagement.domain;

import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
public class UploadFileCommand {

    private Long itemId;
    private MultipartFile[] files;
}
