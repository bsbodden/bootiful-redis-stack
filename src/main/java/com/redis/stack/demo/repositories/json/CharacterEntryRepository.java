package com.redis.stack.demo.repositories.json;

import java.util.List;

import com.redis.om.spring.repository.RedisDocumentRepository;
import com.redis.om.spring.repository.query.autocomplete.AutoCompleteOptions;
import com.redis.stack.demo.models.json.CharacterEntry;

import io.redisearch.Suggestion;

public interface CharacterEntryRepository extends RedisDocumentRepository<CharacterEntry, String> {
  List<Suggestion> autoCompleteName(String query);
  List<Suggestion> autoCompleteName(String query, AutoCompleteOptions options);
}

