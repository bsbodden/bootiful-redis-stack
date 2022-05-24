package com.redis.stack.demo.models.hashes;

import org.springframework.data.redis.core.RedisHash;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@RedisHash
public class Role {
  private String id;
  private String name;
}
