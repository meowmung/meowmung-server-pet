package com.example.pet.repository;

import com.example.pet.entity.Pet;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByUserId(Long userId);

    Optional<Pet> findById(Long petId);
}
