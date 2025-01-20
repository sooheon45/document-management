package documentmanagement.domain;

import documentmanagement.domain.*;
import documentmanagement.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class FileSaved extends AbstractEvent {

    private Long id;
    private String name;
    private String userId;
    private String userName;
    private String text;
    private Date timeStamp;
    private String status;
    private String reason;

    public FileSaved(Document aggregate) {
        super(aggregate);
    }

    public FileSaved() {
        super();
    }
}
//>>> DDD / Domain Event
