package com.oos12.scansavvy.health.repository;

import com.oos12.scansavvy.health.model.Health;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HealthRepository extends MongoRepository<Health, String> {
}
