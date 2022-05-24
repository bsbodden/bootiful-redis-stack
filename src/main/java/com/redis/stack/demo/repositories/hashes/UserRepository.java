package com.redis.stack.demo.repositories.hashes;

import com.redis.stack.demo.models.hashes.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
}
