package com.example.pet.dto.use;

import java.util.List;

public record SendPetDto(
        Long petId,
        List<String> concerneds
) {
}
