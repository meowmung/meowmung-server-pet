package com.example.pet.repository;

import com.example.pet.entity.code.Breed;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BreedRepository extends JpaRepository<Breed, String> {
    Optional<Breed> findByName(String breedName);

}
