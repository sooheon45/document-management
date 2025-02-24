package documentmanagement.domain;

import documentmanagement.domain.*;
import documentmanagement.infra.AbstractEvent;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class FileSaved extends AbstractEvent {

    private Long id;
    private Long itemId;
    private String name;
    private String filePath;
    private String fileType;
    private String previewPath;
    private String status;
    private Date timeStamp;
    private String reason;

    public FileSaved(Document aggregate) {
        super(aggregate);
    }

    public FileSaved() {
        super();
    }
}
//>>> DDD / Domain Event
