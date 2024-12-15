package com.example.pet.service;

import com.example.pet.dto.use.RecommendResultsDto;
import com.example.pet.dto.use.FirstRecommendedDto;
import com.example.pet.dto.request.PetRequest;
import com.example.pet.dto.use.PetResponse;
import com.example.pet.dto.use.SecondRecommendedDto;
import com.example.pet.entity.AdditionalInfo;
import com.example.pet.entity.Concerned;
import com.example.pet.entity.Pet;
import com.example.pet.entity.RecommendedResults;
import com.example.pet.entity.RecommendedTerms;
import com.example.pet.entity.code.Breed;
import com.example.pet.repository.AdditionalInfoRepository;
import com.example.pet.repository.ConcernedRepository;
import com.example.pet.repository.PetRepository;
import com.example.pet.repository.RecommendedResultsRepository;
import com.example.pet.repository.RecommendedTermsRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetService {
    private final BreedService breedService;
    private final PetRepository petRepository;
    private final RecommendedResultsRepository recommendedResultsRepository;
    private final RecommendedTermsRepository recommendedTermsRepository;
    private final AdditionalInfoRepository additionalInfoRepository;
    private final ConcernedRepository concernedRepository;

    // 펫 정보 등록
    public PetResponse savePet(PetRequest petRequest, Long userId) {
        Breed breed = breedService.getBreedCode(petRequest.breedName());
        System.out.println(breed.getCode());
        System.out.println(breed.getName());
        System.out.println(breed.getType());
        Pet pet = petRequest.toEntity(breed, userId);
        petRepository.save(pet);
        return PetResponse.fromEntity(pet);
    }

    // 사용자의 모든 펫 정보 조회
    public List<PetResponse> getAllPetsByUserId(Long id) {
        List<PetResponse> petResponses = new ArrayList<>();
        for (Pet pet : petRepository.findByUserId(id)) {
            petResponses.add(PetResponse.fromEntity(pet));
        }
        return petResponses;
    }

    // 1차 후 펫 정보 저장
    public String saveUpdatedPet(FirstRecommendedDto firstRecommendedDto) {
        Pet pet = petRepository.findById(firstRecommendedDto.sendPetDto().petId())
                .orElseThrow(() -> new RuntimeException(
                        "펫ID " + firstRecommendedDto.sendPetDto().petId() + "찾을 수 없습니다."));

        // 펫 정보 저장
        List<Concerned> concerneds = new ArrayList<>();
        for (String concernedName : firstRecommendedDto.sendPetDto().concerneds()) {
            Concerned concerned = Concerned.builder()
                    .pet(pet)
                    .name(concernedName)
                    .build();
            concerneds.add(concerned);
            concernedRepository.save(concerned);
        }
        pet.addConcerned(concerneds);
        petRepository.save(pet);

        // 보험 결과 저장
        for (RecommendResultsDto recommendResultsDto : firstRecommendedDto.recommendResultsDto()) {
            List<RecommendedTerms> recommendedTermsList = new ArrayList<>();
            for (String termId : recommendResultsDto.termId()) {
                RecommendedTerms recommendedTerms = RecommendedTerms.builder()
                        .termId(termId)
                        .build();
                recommendedTermsRepository.save(recommendedTerms);
                recommendedTermsList.add(recommendedTerms);
            }

            RecommendedResults recommendedResults = RecommendedResults.builder()
                    .pet(pet)
                    .level(recommendResultsDto.level())
                    .insuranceId(recommendResultsDto.insuranceId())
                    .recommendedTerms(recommendedTermsList)
                    .build();
            recommendedTermsList.forEach(recommendedTerms -> {
                recommendedTerms.addRecomendedResults(recommendedResults);
            });
            recommendedResultsRepository.save(recommendedResults);

        }
        return pet.getPetName();
    }

    // 2차 후 펫 정보 저장, 보험 결과 저장
    public String saveAdditionalUpdatedPet(SecondRecommendedDto SecondRecommendedDto) {
        System.out.println(SecondRecommendedDto.sendAdditionalPetInfoDto().petId());

        Pet pet = petRepository.findById(SecondRecommendedDto.sendAdditionalPetInfoDto().petId())
                .orElseThrow(() -> new RuntimeException(
                        "펫ID " + SecondRecommendedDto.sendAdditionalPetInfoDto().petId() + " not found."));
        System.out.println("펫id" + pet.getPetId());
        // 펫 정보 저장
        AdditionalInfo additionalInfo = AdditionalInfo.builder()
                .weight(SecondRecommendedDto.sendAdditionalPetInfoDto().weight())
                .foodCount(SecondRecommendedDto.sendAdditionalPetInfoDto().foodCount())
                .currentDisease(SecondRecommendedDto.sendAdditionalPetInfoDto().currentDisease())
                .build();
        additionalInfoRepository.save(additionalInfo);
        pet.addAdditionalInfo(additionalInfo);
        petRepository.save(pet);

        // 보험 정보 저장
        List<RecommendedTerms> recommendedTermsList = new ArrayList<>();
        for (String termId : SecondRecommendedDto.recommendResultsDto().termId()) {
            RecommendedTerms recommendedTerms = RecommendedTerms.builder()
                    .termId(termId)
                    .build();
            recommendedTermsRepository.save(recommendedTerms);
            recommendedTermsList.add(recommendedTerms);
        }

        RecommendedResults recommendedResults = RecommendedResults.builder()
                .pet(pet)
                .level(SecondRecommendedDto.recommendResultsDto().level())
                .insuranceId(SecondRecommendedDto.recommendResultsDto().insuranceId())
                .recommendedTerms(recommendedTermsList)
                .build();
        recommendedTermsList.forEach(recommendedTerms -> {
            recommendedTerms.addRecomendedResults(recommendedResults);
        });
        recommendedResultsRepository.save(recommendedResults);
        return pet.getPetName();

    }
}
