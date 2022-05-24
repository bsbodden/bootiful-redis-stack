package com.redis.stack.demo.repositories.json;

import com.redis.om.spring.repository.RedisDocumentRepository;
import com.redis.stack.demo.models.json.FictionalCharacter;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;

public interface FictionalCharacterRepository extends RedisDocumentRepository<FictionalCharacter, String> {
  // Find people by age range
  Iterable<FictionalCharacter> findByActorAgeBetween(int minAge, int maxAge);

  // Find people by their first and last name
  Iterable<FictionalCharacter> findByActorFirstNameAndActorLastName(String firstName, String lastName);

  // Draws a circular geofilter around a spot and returns all people in that
  // radius
  Iterable<FictionalCharacter> findByActorLocationNear(Point point, Distance distance);
}
