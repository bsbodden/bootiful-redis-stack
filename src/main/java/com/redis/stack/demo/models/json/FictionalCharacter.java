package com.redis.stack.demo.models.json;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;

import com.redis.om.spring.annotations.Document;
import com.redis.om.spring.annotations.Indexed;
import com.redis.om.spring.annotations.Searchable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Document
public class FictionalCharacter {
  // Id Field, also indexed
  @Id
  @Indexed
  private String id;

  // Indexed for exact text matching
  @Indexed
  @NonNull
  private String actorFirstName;

  @Indexed
  @NonNull
  private String actorLastName;

  // Indexed for numeric matches
  @Indexed
  @NonNull
  private Integer actorAge;

  // Indexed for Full Text matches
  @Searchable
  @NonNull
  private String quote;

  // Indexed for Geo Filtering
  @Indexed
  @NonNull
  private Point actorLocation;

  // Nest indexed object
  @Indexed
  @NonNull
  private Address actorAddress;

  @Indexed
  @NonNull
  private Set<String> skills;
}