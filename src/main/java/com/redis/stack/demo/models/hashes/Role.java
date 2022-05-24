package com.redis.stack.demo.models.hashes;

import com.redis.om.spring.annotations.Indexed;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@RedisHash
public class Role {
  @Id
  private String id;

  @Indexed
  private String name;
}
