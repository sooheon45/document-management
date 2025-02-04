package documentmanagement.domain;

import java.util.*;
import lombok.Data;

@Data
public class UploadFileCommand {

    private Long id;
    private String name;
    private String filePath;
    private String previewPath;
    private String status;
    private Date timeStamp;
    private String reason;
}
