package com.example.desafioSpring.repositories;

import com.example.desafioSpring.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
