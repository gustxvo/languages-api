package br.com.alura.languages.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "languages")
public record Language (@Id String id, String title, String image, Integer ranking) {}