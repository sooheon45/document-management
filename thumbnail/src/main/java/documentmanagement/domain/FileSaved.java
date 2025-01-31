package documentmanagement.domain;

import documentmanagement.domain.*;
import documentmanagement.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class FileSaved extends AbstractEvent {

    private Long id;
    private String name;
    private String userId;
    private String userName;
    private String fileType;
    private String filePath;
    private Date timeStamp;
    private String status;
    private String reason;
}
