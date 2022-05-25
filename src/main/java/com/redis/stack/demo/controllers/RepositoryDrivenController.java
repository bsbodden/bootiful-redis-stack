package com.redis.stack.demo.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.redis.om.spring.repository.query.autocomplete.AutoCompleteOptions;
import com.redis.stack.demo.models.json.FictionalCharacter;
import com.redis.stack.demo.repositories.hashes.UserRepository;
import com.redis.stack.demo.repositories.json.CharacterEntryRepository;
import com.redis.stack.demo.repositories.json.FictionalCharacterRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.redisearch.Suggestion;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.domain.geo.Metrics;

@RestController
@RequestMapping("/api/rdc/")
public class RepositoryDrivenController {
  @Autowired
  FictionalCharacterRepository repo;

  @Autowired
  UserRepository userRepo;

  @Autowired
  CharacterEntryRepository ceRepo;

  @GetMapping("all")
  Iterable<FictionalCharacter> all() {
    return repo.findAll();
  }

  @GetMapping("{id}")
  Optional<FictionalCharacter> byId(@PathVariable String id) {
    return repo.findById(id);
  }

  @GetMapping("age_between")
  Iterable<FictionalCharacter> byAgeBetween( //
      @RequestParam("min") int min, //
      @RequestParam("max") int max) {
    return repo.findByActorAgeBetween(min, max);
  }

  @GetMapping("name")
  Iterable<FictionalCharacter> byFirstNameAndLastName(@RequestParam("first") String firstName, //
      @RequestParam("last") String lastName) {
    return repo.findByActorFirstNameAndActorLastName(firstName, lastName);
  }

  @GetMapping("homeloc")
  Iterable<FictionalCharacter> byHomeLoc(//
      @RequestParam("lat") double lat, //
      @RequestParam("lon") double lon, //
      @RequestParam("d") double distance) {
    return repo.findByActorLocationNear(new Point(lon, lat), new Distance(distance, Metrics.MILES));
  }

  @GetMapping("statement")
  Iterable<FictionalCharacter> byPersonalStatement(@RequestParam("q") String q) {
    return repo.searchByQuote(q);
  }

  @GetMapping("city")
  Iterable<FictionalCharacter> byCity(@RequestParam("city") String city) {
    return repo.findByActorAddress_City(city);
  }

  @GetMapping("skills")
  Iterable<FictionalCharacter> byAnySkills(@RequestParam("skills") Set<String> skills) {
    return repo.findBySkills(skills);
  }

  @GetMapping("skills/all")
  Iterable<FictionalCharacter> byAllSkills(@RequestParam("skills") Set<String> skills) {
    return repo.findBySkillsContainingAll(skills);
  }

  @GetMapping("email_taken/{email}")
  boolean isEmailTaken(@PathVariable String email) {
    return userRepo.existsByEmail(email);
  }

  @GetMapping("autocomplete/{query}")
  List<Suggestion> autocomplete(@PathVariable String query) {
    return ceRepo.autoCompleteName(query, AutoCompleteOptions.get().withPayload());
  }
}
