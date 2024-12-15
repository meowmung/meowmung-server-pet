package com.example.pet.dto.use;

import com.example.pet.entity.Pet;

public record PetResponse(
        Long userId,
        Long petId,
        String petName,
        Integer age,
        Boolean gender,
        Boolean neutered,
        String petType,
        String breedName,
        int breedCode
) {
    public static PetResponse fromEntity(Pet pet) {
        return new PetResponse(
                pet.getUserId(),
                pet.getPetId(),
                pet.getPetName(),
                pet.getAge(),
                pet.getGender(),
                pet.getNeutered(),
                pet.getBreed().getType(),
                pet.getBreed().getName(),
                pet.getBreed().getCode()
        );
    }
}
