package documentmanagement.domain;

import java.util.*;
import lombok.Data;

import java.io.File;
// import org.springframework.web.multipart.MultipartFile;
import java.sql.Blob;

@Data
public class SaveFileCommand {

    private Long id;
    private String name;
    private String userId;
    private String userName;
    private String fileType;
    private File file;
    private String text;
    private Date timeStamp;
}
