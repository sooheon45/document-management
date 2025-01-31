package documentmanagement.infra;

import documentmanagement.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class ThumbnailHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Thumbnail>> {

    @Override
    public EntityModel<Thumbnail> process(EntityModel<Thumbnail> model) {
        return model;
    }
}
