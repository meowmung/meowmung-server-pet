package com.example.pet.dto.use;

public record SendAdditionalPetInfo(
        Long petId,
        Integer weight,
        Integer foodCount,
        String currentDisease
) {
}
