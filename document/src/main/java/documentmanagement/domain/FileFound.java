package documentmanagement.domain;

import documentmanagement.domain.*;
import documentmanagement.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class FileFound extends AbstractEvent {

    private Long id;
    private String status;
    private String reason;

    public FileFound(Document aggregate) {
        super(aggregate);
    }

    public FileFound() {
        super();
    }
}
//>>> DDD / Domain Event
