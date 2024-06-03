package com.oos12.scansavvy.health.repository;

import com.oos12.scansavvy.health.model.Health;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface HealthRepository extends MongoRepository<Health, String> {
    Health findByEmail(String email);
}
