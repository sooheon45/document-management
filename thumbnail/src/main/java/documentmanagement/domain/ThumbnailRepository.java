package documentmanagement.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "thumbnails",
    path = "thumbnails"
)
public interface ThumbnailRepository
    extends PagingAndSortingRepository<Thumbnail, Long> {}
