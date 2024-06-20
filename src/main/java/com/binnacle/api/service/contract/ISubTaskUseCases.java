package com.binnacle.api.service.contract;

import com.binnacle.api.request.CreateUpdateSubTaskRequest;
import com.binnacle.api.request.CreateUpdateTaskRequest;
import com.binnacle.api.request.DeleteGroupRequest;
import com.binnacle.api.response.PersistResponse;

public interface ISubTaskUseCases {
    PersistResponse create(CreateUpdateSubTaskRequest request);
    PersistResponse update(CreateUpdateSubTaskRequest request);
    PersistResponse delete(DeleteGroupRequest request);
}
