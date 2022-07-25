package br.com.alura.languages.api.dto;

import br.com.alura.languages.api.model.Language;

public record LanguageModel(String id, String name, String image, Integer ranking) {}
