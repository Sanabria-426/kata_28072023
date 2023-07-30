package com.extia.kata.repository;

import com.extia.kata.model.Stack;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStackRepository extends MongoRepository<Stack, String> {
}
