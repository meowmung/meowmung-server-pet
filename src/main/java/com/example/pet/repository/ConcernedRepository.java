package com.example.pet.repository;

import com.example.pet.entity.Concerned;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcernedRepository extends JpaRepository<Concerned, Long> {
}
