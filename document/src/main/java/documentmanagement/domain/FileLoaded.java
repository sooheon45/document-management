package documentmanagement.domain;

import documentmanagement.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class FileLoaded extends AbstractEvent {

    private Long id;
    private String status;
    private String reason;

    public FileLoaded(Document aggregate) {
        super(aggregate);
    }

    public FileLoaded() {
        super();
    }
}
//>>> DDD / Domain Event
