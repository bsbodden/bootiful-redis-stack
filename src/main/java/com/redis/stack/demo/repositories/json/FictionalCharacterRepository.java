package com.redis.stack.demo.repositories.json;

import com.redis.om.spring.repository.RedisDocumentRepository;
import com.redis.stack.demo.models.json.FictionalCharacter;

public interface FictionalCharacterRepository extends RedisDocumentRepository<FictionalCharacter, String> {
}
