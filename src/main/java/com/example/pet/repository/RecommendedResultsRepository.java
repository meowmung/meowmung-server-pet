package com.example.pet.repository;

import com.example.pet.entity.RecommendedResults;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendedResultsRepository extends JpaRepository<RecommendedResults, Long> {
}
