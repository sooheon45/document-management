package documentmanagement.domain;

import documentmanagement.domain.*;
import documentmanagement.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class FilesFound extends AbstractEvent {

    private Long id;
    private String status;
    private String reason;

    public FilesFound(Document aggregate) {
        super(aggregate);
    }

    public FilesFound() {
        super();
    }
}
//>>> DDD / Domain Event
