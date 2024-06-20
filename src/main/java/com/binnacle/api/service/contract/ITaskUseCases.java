package com.binnacle.api.service.contract;

import com.binnacle.api.request.CreateUpdateTaskRequest;
import com.binnacle.api.request.DeleteGroupRequest;
import com.binnacle.api.response.DataResponse;
import com.binnacle.api.response.PersistResponse;

public interface ITaskUseCases {
    PersistResponse create(CreateUpdateTaskRequest request);
    PersistResponse update(CreateUpdateTaskRequest request);
    PersistResponse delete(DeleteGroupRequest request);
}
