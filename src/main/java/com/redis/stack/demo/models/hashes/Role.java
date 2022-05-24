package com.redis.stack.demo.models.hashes;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Role {
  private String id;
  private String name;
}
