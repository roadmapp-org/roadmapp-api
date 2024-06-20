package com.binnacle.api.service.contract;

import com.binnacle.api.request.CreateUpdateProjectRequest;
import com.binnacle.api.request.DeleteGroupRequest;
import com.binnacle.api.response.DataResponse;
import com.binnacle.api.response.PersistResponse;

public interface IProjectUseCases {
    PersistResponse create(CreateUpdateProjectRequest request);
    PersistResponse update(CreateUpdateProjectRequest request);
    PersistResponse delete(DeleteGroupRequest request);
    DataResponse getMyProjects();
}
