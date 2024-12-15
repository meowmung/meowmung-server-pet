package com.example.pet.dto.request;

import com.example.pet.entity.Pet;
import com.example.pet.entity.code.Breed;

// 첫 펫 정보 등록
public record PetRequest(
        String petName,
        Integer age,
        Boolean gender,
        Boolean neutered,
        String breedName
) {
    public Pet toEntity(Breed breed, Long userId) {
        return Pet.builder()
                .userId(userId)
                .petName(petName)
                .age(age)
                .gender(gender)
                .neutered(neutered)
                .breed(breed)
                .build();
    }
}





