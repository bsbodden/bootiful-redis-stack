package com.redis.stack.demo.controllers;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.redis.stack.demo.models.json.FictionalCharacter;
import com.redis.stack.demo.models.json.FictionalCharacter$;
import com.redis.om.spring.search.stream.EntityStream;

import io.redisearch.aggregation.SortedField.SortOrder;

@RestController
@RequestMapping("/api/esdc")
public class EntityStreamDrivenController {
  @Autowired
  EntityStream entityStream;

  @GetMapping("age_between")
  Iterable<FictionalCharacter> byActorAgeBetween(@RequestParam("min") int min, //
      @RequestParam("max") int max) {
    return entityStream //
        .of(FictionalCharacter.class) //
        .filter(FictionalCharacter$.ACTOR_AGE.between(min, max)) //
        .sorted(FictionalCharacter$.ACTOR_AGE, SortOrder.ASC) //
        .limit(100) //
        .collect(Collectors.toList());
  }

  @GetMapping("name")
  Iterable<FictionalCharacter> byFirstNameAndLastName(
      @RequestParam("first") String firstName, //
      @RequestParam("last") String lastName) {
    return entityStream //
        .of(FictionalCharacter.class) //
        .filter(FictionalCharacter$.ACTOR_FIRST_NAME.eq(firstName)) //
        .filter(FictionalCharacter$.ACTOR_LAST_NAME.eq(lastName)) //
        .limit(100) //
        .collect(Collectors.toList());
  }

  @GetMapping("homeloc")
  Iterable<FictionalCharacter> byHomeLoc(//
      @RequestParam("lat") double lat, //
      @RequestParam("lon") double lon, //
      @RequestParam("d") double distance) {
    return entityStream //
        .of(FictionalCharacter.class) //
        .filter(FictionalCharacter$.ACTOR_LOCATION.near(new Point(lon, lat), new Distance(distance, Metrics.MILES))) //
        .limit(100) //
        .collect(Collectors.toList());
  }

}
