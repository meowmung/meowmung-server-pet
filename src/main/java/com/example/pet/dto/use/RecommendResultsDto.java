package com.example.pet.dto.use;

import java.util.List;
import lombok.Builder;

@Builder
public record RecommendResultsDto(
        Long petId,
        int level,
        String insuranceId,
        List<String> termId
) {
}
