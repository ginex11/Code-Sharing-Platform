package platform;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@org.springframework.stereotype.Repository
public interface Repository extends CrudRepository<CodeObject, Long> {
    @Query(value = "select * from CODE_OBJECT c where c.id = ?1", nativeQuery = true)
    Optional<CodeObject> getSnippetByUuid(UUID id);

    @Query(value = "select * from CODE_OBJECT c where c.views > 0 and c.time > 0", nativeQuery = true)
    Optional<CodeObject> getAllSnippetsByUuid(UUID id);

    @Query(value = "select * from CODE_OBJECT c where c.RESTRICTED_BY_TIME = 0 and c.RESTRICTED_BY_VIEW = 0", nativeQuery = true)
    List<CodeObject> getAllNotRestrictedSnippets();
}
