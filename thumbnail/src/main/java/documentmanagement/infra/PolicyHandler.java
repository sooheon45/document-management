package documentmanagement.infra;

import documentmanagement.config.kafka.KafkaProcessor;
import documentmanagement.domain.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    ThumbnailRepository thumbnailRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='FileSaved'"
    )
    public void wheneverFileSaved_ProcessThumbnail(
        @Payload FileSaved fileSaved
    ) {
        FileSaved event = fileSaved;
        System.out.println("\n\n##### listener ProcessThumbnail : " + fileSaved + "\n\n");

        // Sample Logic //
        Thumbnail thumbnail = new Thumbnail();
        thumbnail.processThumbnail(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
