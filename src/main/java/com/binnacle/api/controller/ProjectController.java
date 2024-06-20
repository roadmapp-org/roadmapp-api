package com.binnacle.api.controller;

import com.binnacle.api.request.CreateUpdateProjectRequest;
import com.binnacle.api.request.DeleteGroupRequest;
import com.binnacle.api.response.DataResponse;
import com.binnacle.api.response.ErrorResponse;
import com.binnacle.api.response.PersistResponse;
import com.binnacle.api.service.contract.IProjectUseCases;
import com.binnacle.api.utils.Results;
import com.binnacle.api.utils.Tools;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("project")
@RequiredArgsConstructor
public class ProjectController {

    private final IProjectUseCases projectUseCases;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> create(@Valid @RequestBody CreateUpdateProjectRequest request, BindingResult bindingResult) {
        ResponseEntity<ErrorResponse> errorResponse = Tools.getErrorResponseResponseEntity(bindingResult);
        PersistResponse persistResponse;

        if(errorResponse != null)
        {
            persistResponse = new PersistResponse(Results.VALIDATION_ERROR,errorResponse,null,HttpStatus.BAD_REQUEST);
            return ResponseEntity.status(persistResponse.getStatus()).body(persistResponse);
        }

        persistResponse = projectUseCases.create(request);

        return ResponseEntity.status(persistResponse.getStatus()).body(persistResponse);

    }

    @PatchMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> update(@Valid @RequestBody CreateUpdateProjectRequest request, BindingResult bindingResult) {
        ResponseEntity<ErrorResponse> errorResponse = Tools.getErrorResponseResponseEntity(bindingResult);
        PersistResponse persistResponse;

        if(errorResponse != null)
        {
            persistResponse = new PersistResponse(Results.VALIDATION_ERROR,errorResponse,null,HttpStatus.BAD_REQUEST);
            return ResponseEntity.status(persistResponse.getStatus()).body(persistResponse);
        }

        persistResponse = projectUseCases.update(request);

        return ResponseEntity.status(persistResponse.getStatus()).body(persistResponse);

    }

    @DeleteMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> delete(@Valid @RequestBody DeleteGroupRequest request, BindingResult bindingResult) {
        ResponseEntity<ErrorResponse> errorResponse = Tools.getErrorResponseResponseEntity(bindingResult);
        PersistResponse persistResponse;

        if(errorResponse != null)
        {
            persistResponse = new PersistResponse(Results.VALIDATION_ERROR,errorResponse,null,HttpStatus.BAD_REQUEST);
            return ResponseEntity.status(persistResponse.getStatus()).body(persistResponse);
        }

        persistResponse = projectUseCases.delete(request);

        return ResponseEntity.status(persistResponse.getStatus()).body(persistResponse);

    }

    @GetMapping()
    public ResponseEntity<?> myProjects() {
        DataResponse dataResponse = projectUseCases.getMyProjects();
        return ResponseEntity.status(dataResponse.getStatus()).body(dataResponse);
    }

}
