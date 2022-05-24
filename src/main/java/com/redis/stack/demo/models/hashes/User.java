package com.redis.stack.demo.models.hashes;

import java.util.Set;

import com.redis.om.spring.annotations.Searchable;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.annotation.Transient;
import org.springframework.data.redis.core.RedisHash;

import lombok.Data;
import lombok.NonNull;

@Data
@RedisHash
public class User {

  @Id
  private String id;

  @NonNull
  @Searchable
  private String name;

  @NonNull
  private String email;

  @NonNull
  private String password;

  @Transient
  private String passwordConfirm;

  @Reference
  private Set<Role> roles;
}
