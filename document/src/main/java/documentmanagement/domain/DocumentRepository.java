package documentmanagement.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import feign.Param;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "documents", path = "documents")
public interface DocumentRepository extends PagingAndSortingRepository<Document, Long> {
    
    @Query("SELECT d FROM Document d WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Document> findByNameContainingIgnoreCase(@Param("name") String name);
}
