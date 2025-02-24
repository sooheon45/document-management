package documentmanagement.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class DownloadFileCommand {

    private Long id;
    private Long itemId;
}
