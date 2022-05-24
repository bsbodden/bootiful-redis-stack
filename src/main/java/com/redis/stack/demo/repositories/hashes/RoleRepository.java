package com.redis.stack.demo.repositories.hashes;

import java.util.Optional;

import com.redis.stack.demo.models.hashes.Role;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, String> {
  Optional<Role> findFirstByName(String name);
}