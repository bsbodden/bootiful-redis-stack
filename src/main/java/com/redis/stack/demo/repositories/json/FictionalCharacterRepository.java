package com.redis.stack.demo.repositories.json;

import java.util.Set;

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

  // Performs full text search on a characters quote
  Iterable<FictionalCharacter> searchByQuote(String text);

  // Performing a tag search on city
  Iterable<FictionalCharacter> findByActorAddress_City(String city);

  // Search Characters that have one of multiple skills (OR condition)
  Iterable<FictionalCharacter> findBySkills(Set<String> skills);

  // Search Characters that have all of the skills (AND condition):
  Iterable<FictionalCharacter> findBySkillsContainingAll(Set<String> skills);
}
