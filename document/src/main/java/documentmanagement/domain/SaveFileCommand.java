package documentmanagement.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class SaveFileCommand {

    private Long id;
    private String name;
    private String userId;
    private String userName;
    private String text;
    private Date timeStamp;
}
