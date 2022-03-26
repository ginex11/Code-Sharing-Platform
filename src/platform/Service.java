package platform;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static platform.StaticVariables.NO_RESTRICTION;

@org.springframework.stereotype.Service
public class Service {
    @Autowired
    Repository repository;

    public void save(CodeObject codeObject) {
        repository.save(codeObject);
    }

    public Optional<CodeObject> findById(long id) {
        return repository.findById(id);
    }

    public List<CodeObject> findAll() {
        return (List<CodeObject>) repository.findAll();
    }

    public void delete(CodeObject snippet) {
        repository.delete(snippet);
    }

    public Optional<CodeObject> getSnippetByUuid(UUID id) {
        Optional<CodeObject> snippet = repository.getSnippetByUuid(id);
        if (snippet.isPresent()) {
            snippet.get().calcRestTime();
            snippet.get().calcRestViews();
            if (snippet.get().isExpired()) {
                this.delete(snippet.get());
                return Optional.empty();
            }
            save(snippet.get());
        }
        return snippet;

    }

}

