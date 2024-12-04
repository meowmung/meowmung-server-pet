package com.example.pet.service;

import com.example.pet.entity.code.Breed;
import com.example.pet.repository.BreedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BreedServiceImpl implements BreedService {
    private final BreedRepository breedRepository;

    @Override
    // 종 코드 찾기
    public Breed getBreedCode(String breedName) {
        System.out.println(breedName);
        return breedRepository.findById(breedName)
                .orElseThrow(() -> new RuntimeException("그런 종 없는데요"));
    }

}