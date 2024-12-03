package com.example.pet.repository;

import com.example.pet.entity.RecommendedTerms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendedTermsRepository extends JpaRepository<RecommendedTerms, Long> {
}
