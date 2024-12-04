package com.example.pet.dto.use;

import com.example.pet.entity.Pet;

public record PetDto(
        Long petId,
        String petName,
        Integer age,
        Boolean gender,
        Boolean neutered,
        String breedName,
        String petType
) {
    public static PetDto fromEntity(Pet pet) {
        return new PetDto(
                pet.getPetId(),
                pet.getPetName(),
                pet.getAge(),
                pet.getGender(),
                pet.getNeutered(),
                pet.getBreed().getName(),
                pet.getBreed().getType()
        );
    }
}
