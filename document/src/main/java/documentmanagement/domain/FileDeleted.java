package documentmanagement.domain;

import documentmanagement.domain.*;
import documentmanagement.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class FileDeleted extends AbstractEvent {

    private Long id;
    private Long itemId;
    private String status;
    private String reason;

    public FileDeleted(Document aggregate) {
        super(aggregate);
    }

    public FileDeleted() {
        super();
    }
}
//>>> DDD / Domain Event
