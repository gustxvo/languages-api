package br.com.alura.languages.api.controller;

import br.com.alura.languages.api.dto.LanguageModel;
import br.com.alura.languages.api.model.Language;
import br.com.alura.languages.api.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/languages")
// TODO: Refractor: Create class to handle business rules
public class LanguageController {

    @Autowired
    private LanguageRepository languageRepository;

    @GetMapping()
    public List<LanguageModel> list() {
        return languageRepository.findAll().stream()
                .map(language -> new LanguageModel(
                        language.id(), language.title(), language.image(), language.ranking()
                )).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LanguageModel> getLanguage(@PathVariable String id) {
        return languageRepository.findById(id)
                .map(language -> {
                    LanguageModel languageModel = new LanguageModel(
                            language.id(), language.title(), language.image(), language.ranking()
                    );
                    return ResponseEntity.ok(languageModel);
                }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LanguageModel create(@RequestBody Language language) {
        Language newLanguage = languageRepository.save(language);
        return new LanguageModel(newLanguage.id(), language.title(), language.image(), language.ranking());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<LanguageModel> update(@PathVariable String id,
                @RequestBody Language language) {
        if (!languageRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        Language updatedLanguage = new Language(id, language.title(), language.image(), language.ranking());
        languageRepository.save(updatedLanguage);

        LanguageModel languageModel = new LanguageModel(
                updatedLanguage.id(), language.title(), language.image(), language.ranking());
        return ResponseEntity.ok(languageModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (!languageRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        languageRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
