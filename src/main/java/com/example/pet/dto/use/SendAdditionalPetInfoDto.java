package com.example.pet.dto.use;

public record SendAdditionalPetInfoDto(
        Long petId,
        Integer weight,
        Integer foodCount,
        String currentDisease
) {
}
