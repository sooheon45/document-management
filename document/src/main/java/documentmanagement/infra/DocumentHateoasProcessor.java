package documentmanagement.infra;

import documentmanagement.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class DocumentHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Document>> {

    @Override
    public EntityModel<Document> process(EntityModel<Document> model) {
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/savefile")
                .withRel("savefile")
        );
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/searchtext")
                .withRel("searchtext")
        );
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/downloadfile")
                .withRel("downloadfile")
        );
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/findfile")
                .withRel("findfile")
        );

        return model;
    }
}
