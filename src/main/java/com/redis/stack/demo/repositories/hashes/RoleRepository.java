package com.redis.stack.demo.repositories.hashes;

import com.redis.stack.demo.models.hashes.Role;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, String> {
}