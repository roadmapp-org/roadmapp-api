package com.binnacle.api.controller;

import com.binnacle.api.request.CreateUpdateLogRequest;
import com.binnacle.api.request.CreateUpdateProjectRequest;
import com.binnacle.api.request.LogFilterRequest;
import com.binnacle.api.response.DataResponse;
import com.binnacle.api.response.ErrorResponse;
import com.binnacle.api.response.PersistResponse;
import com.binnacle.api.service.contract.ILogUseCases;
import com.binnacle.api.utils.Results;
import com.binnacle.api.utils.Tools;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("logs")
@RequiredArgsConstructor
public class LogController {
    private final ILogUseCases logUseCases;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> create(@Valid @RequestBody CreateUpdateLogRequest request, BindingResult bindingResult) {
        ResponseEntity<ErrorResponse> errorResponse = Tools.getErrorResponseResponseEntity(bindingResult);
        PersistResponse persistResponse;

        if(errorResponse != null)
        {
            persistResponse = new PersistResponse(Results.VALIDATION_ERROR,errorResponse,null, HttpStatus.BAD_REQUEST);
            return ResponseEntity.status(persistResponse.getStatus()).body(persistResponse);
        }

        persistResponse = logUseCases.create(request);

        return ResponseEntity.status(persistResponse.getStatus()).body(persistResponse);

    }



    @GetMapping
    public ResponseEntity<?> getLatestByOwner() {
        DataResponse dataResponse = logUseCases.getLatestByOwner();
        return ResponseEntity.status(dataResponse.getStatus()).body(dataResponse);
    }

    @GetMapping("/filtered")
    public ResponseEntity<?> getFiltered(@RequestParam int project, @RequestParam int task, @RequestParam int subtask, @RequestParam int alreadySent) {
        DataResponse dataResponse = logUseCases.getFiltered(project, task, subtask, alreadySent);
        return ResponseEntity.status(dataResponse.getStatus()).body(dataResponse);
    }




}
