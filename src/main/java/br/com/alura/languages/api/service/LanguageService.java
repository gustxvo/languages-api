package br.com.alura.languages.api.service;

import br.com.alura.languages.api.dto.LanguageModel;
import br.com.alura.languages.api.model.Language;
import br.com.alura.languages.api.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LanguageService {

    @Autowired
    LanguageRepository languageRepository;

    public List<LanguageModel> list() {
        return languageRepository.findAll().stream()
                .map(language -> new LanguageModel(
                        language.getId(), language.getTitle(),
                        language.getImage(), language.getRanking()
                )).collect(Collectors.toList());
    }

    public ResponseEntity<LanguageModel> getLanguageById(String id) {
        return languageRepository.findById(id)
                .map(language -> {
                    LanguageModel languageModel = new LanguageModel(
                            language.getId(), language.getTitle(),
                            language.getImage(), language.getRanking()
                    );
                    return ResponseEntity.ok(languageModel);
                }).orElse(ResponseEntity.notFound().build());
    }

    @Transactional
    public LanguageModel save(Language language) {
        languageRepository.save(language);
        return new LanguageModel(language.getId(), language.getTitle(),
                language.getImage(), language.getRanking());
    }

    public ResponseEntity<Void> deleteById(String id) {
        if (!languageRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        languageRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
