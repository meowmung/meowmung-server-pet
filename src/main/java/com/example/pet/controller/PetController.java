package com.example.pet.controller;

import com.example.pet.dto.use.FirstRecommendedDto;
import com.example.pet.dto.request.PetRequest;
import com.example.pet.dto.use.PetResponse;
import com.example.pet.dto.use.SecondRecommendedDto;
import com.example.pet.service.PetService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pet")
@RequiredArgsConstructor
public class PetController {
    private final PetService petService;

    // 펫 등록
    @PostMapping
    public ResponseEntity<PetResponse> enrollPet(@RequestBody PetRequest petRequest,
                                                 @RequestHeader(name = "X-Authorization-memberId") Long userId) {
        PetResponse savedPet = petService.savePet(petRequest, userId);
        return ResponseEntity.ok(savedPet);
    }

    // 펫 정보 주기
    // 1. 해당 사용자의 등록된 모든 펫 조회
    @GetMapping
    public List<PetResponse> getAllPet(@RequestHeader(name = "X-Authorization-memberId") Long userId) {
        return petService.getAllPetsByUserId(userId);
    }

//    // 여러 펫 중에 선택한 펫 정보
//    @GetMapping("/mypet/{petId}")
//    public PetResponse getPet(@PathVariable(name = "petId") Long petId) {
//        petServiceImpl.sendPetInfo(petId);
//        return petServiceImpl.getPetByPetId(petId);
//    }

    // 1차 후
    // 우려 질병 저장, 1차 결과 저장
    @PostMapping("/recommend")
    public String updatePetInfo(@RequestBody FirstRecommendedDto firstRecommendedDto) {
        petService.saveUpdatedPet(firstRecommendedDto);
        return "저장";
    }

//    @GetMapping("/recommend")
//    public FirstResultResponse getFirstResponse() {
//        FirstResultResponse firstResultResponse = FirstResultResponse.fromEntity()
//        return
//    }

    // 2차 후
    @PostMapping("/additional")
    public String additionalUpdatedPet(@RequestBody SecondRecommendedDto SecondRecommendedDto) {
        petService.saveAdditionalUpdatedPet(SecondRecommendedDto);
        return "저장";
    }

}
