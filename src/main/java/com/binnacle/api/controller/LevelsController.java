package com.binnacle.api.controller;

import com.binnacle.api.response.DataResponse;
import com.binnacle.api.service.contract.ILevelUseCases;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/levels")
@RequiredArgsConstructor
public class LevelsController {

    private final ILevelUseCases levelUseCases;

    @GetMapping
    public ResponseEntity<?> getHomeData() {
        DataResponse dataResponse = levelUseCases.getLevels();
        return ResponseEntity.status(dataResponse.getStatus()).body(dataResponse);
    }

}
