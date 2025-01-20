package documentmanagement.domain;

import documentmanagement.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "documents", path = "documents")
public interface DocumentRepository
    extends PagingAndSortingRepository<Document, Long> {}
