package com.extia.kata.repository;

import com.extia.kata.model.Operand;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOperandRepository extends MongoRepository<Operand, String> {
}
