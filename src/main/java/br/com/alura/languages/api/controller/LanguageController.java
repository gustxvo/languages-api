package br.com.alura.languages.api.controller;

import br.com.alura.languages.api.dto.LanguageModel;
import br.com.alura.languages.api.model.Language;
import br.com.alura.languages.api.repository.LanguageRepository;
import br.com.alura.languages.api.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/languages")
public class LanguageController {

    @Autowired
    private LanguageRepository languageRepository;

    private final LanguageService languageService;

    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping()
    public List<LanguageModel> listLanguages() {
        return languageService.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LanguageModel> getLanguage(@PathVariable String id) {
        return languageService.getLanguageById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LanguageModel create(@RequestBody Language language) {
        return languageService.save(language);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<LanguageModel> update(@PathVariable String id,
                @RequestBody Language language) {
        if (!languageRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        language.setId(id);
        return ResponseEntity.ok(languageService.save(language));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        return languageService.deleteById(id);
    }
}
