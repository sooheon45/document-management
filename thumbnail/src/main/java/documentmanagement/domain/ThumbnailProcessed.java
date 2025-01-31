package documentmanagement.domain;

import documentmanagement.domain.*;
import documentmanagement.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class ThumbnailProcessed extends AbstractEvent {

    private Long id;
    private String status;
    private String reason;

    public ThumbnailProcessed(Thumbnail aggregate) {
        super(aggregate);
    }

    public ThumbnailProcessed() {
        super();
    }
}
//>>> DDD / Domain Event
