package platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static platform.StaticVariables.ALL_SNIPPETS_LIMIT;
import static platform.StaticVariables.FORMATTER;


@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    Service service;

    @GetMapping("/api/code/{id}")
    public ResponseEntity<CodeObject> getCodeAPI(@PathVariable UUID id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json");
        Optional<CodeObject> code1 = service.getSnippetByUuid(id);
        return new ResponseEntity<>(code1.orElse(null), responseHeaders,
                code1.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @GetMapping("/api/code/latest")
    public ResponseEntity<List<CodeObject>> getLatestCodeAPI() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json");
        List<CodeObject> tmpList = service.repository.getAllNotRestrictedSnippets();
        tmpList.sort(Comparator.comparing(CodeObject::getDateNano).reversed());
        return new ResponseEntity<>(tmpList.stream().limit(ALL_SNIPPETS_LIMIT).collect(Collectors.toList()),
                responseHeaders, HttpStatus.OK);
    }

    @PostMapping("/api/code/new")
    public ResponseEntity<String> newCodeSnippetAPI(@RequestBody CodeObject newSnippet) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json");
        newSnippet.setDateNano(LocalDateTime.now());
        newSnippet.setDate(newSnippet.getDateNano().format(FORMATTER));
        newSnippet.setRestrictedByView(newSnippet.getViews() > 0);
        newSnippet.setRestrictedByTime(newSnippet.getTime() > 0);
        service.save(newSnippet);
        return new ResponseEntity<>("{\"id\" :\"" + newSnippet.getId() + "\"}", responseHeaders, HttpStatus.OK);
    }

    /*
     *************************************************WebInterface methods*************************************************
     */
    @GetMapping("/code/new")
    public String newCodeWeb() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "text/html");
        return "newSnippet";

    }

    @GetMapping("/code/latest")
    public String newLatestCodeWeb(Model model) {
        List<CodeObject> tmpList = service.repository.getAllNotRestrictedSnippets();
        tmpList.sort(Comparator.comparing(CodeObject::getDateNano).reversed());
        model.addAttribute("allCodeSnippets", tmpList.stream().limit(ALL_SNIPPETS_LIMIT).collect(Collectors.toList()));
        return "lastCodeSnippets";
    }

    @GetMapping(value = "/code/{id}")
    public String getCodeWeb(@PathVariable UUID id, Model model) {
        Optional<CodeObject> code = service.getSnippetByUuid(id);
        if(code.isPresent()&&code.get()!=null){
            model.addAttribute("snippet", code.get());
            return "codeSnippet";
        }
        return"";
    }

}
