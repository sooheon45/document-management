package documentmanagement.domain;

import documentmanagement.domain.*;
import documentmanagement.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class TextSearched extends AbstractEvent {

    private Integer size;
    private String status;
    private String reason;

    public TextSearched(Document aggregate) {
        super(aggregate);
    }

    public TextSearched() {
        super();
    }
}
//>>> DDD / Domain Event
