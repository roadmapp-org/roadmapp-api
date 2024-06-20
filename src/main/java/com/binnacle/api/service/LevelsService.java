package com.binnacle.api.service;

import com.binnacle.api.repository.contract.IProjectRepository;
import com.binnacle.api.repository.contract.ISubTaskRepository;
import com.binnacle.api.repository.contract.ITaskRepository;
import com.binnacle.api.response.DataResponse;
import com.binnacle.api.response.home.HomeResponse;
import com.binnacle.api.response.project.ProjectResponse;
import com.binnacle.api.response.subtask.SubtaskResponse;
import com.binnacle.api.response.task.TaskResponse;
import com.binnacle.api.service.contract.ILevelUseCases;
import com.binnacle.api.utils.Results;
import com.binnacle.api.utils.Tools;
import com.binnacle.api.utils.errors.ErrorCodes;
import com.binnacle.api.utils.errors.ErrorDescriptions;
import com.binnacle.api.utils.exceptions.ErrorWhenRetreivingDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LevelsService implements ILevelUseCases {
    private final IProjectRepository projectRepository;
    private final ITaskRepository taskRepository;
    private final ISubTaskRepository subTaskRepository;

    @Override
    public DataResponse getLevels() {
        DataResponse dataResponse = new DataResponse();
        HomeResponse homeResponse = new HomeResponse();

        List<ProjectResponse> projectList;
        List<TaskResponse> taskList;
        List<SubtaskResponse> subtaskList;

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            projectList = projectRepository.getAllByOwner(name);
            if(projectList == null)
                throw new ErrorWhenRetreivingDataException(ErrorCodes.ERROR_WHEN_RETREIVING_DATA, ErrorDescriptions.ERROR_WHEN_RETREIVING_DATA);

            taskList = taskRepository.getAllByOwner(name);
            if(taskList == null)
                throw new ErrorWhenRetreivingDataException(ErrorCodes.ERROR_WHEN_RETREIVING_DATA, ErrorDescriptions.ERROR_WHEN_RETREIVING_DATA);

            subtaskList = subTaskRepository.getAllByOwner(name);
            if(subtaskList == null)
                throw new ErrorWhenRetreivingDataException(ErrorCodes.ERROR_WHEN_RETREIVING_DATA, ErrorDescriptions.ERROR_WHEN_RETREIVING_DATA);

            homeResponse.setProjectList(projectList);
            homeResponse.setTaskList(taskList);
            homeResponse.setSubtaskList(subtaskList);

            dataResponse = new DataResponse(Results.OK,"",homeResponse, HttpStatus.OK);

        } catch(Exception e) {
            dataResponse = Tools.getDataResponseError(e,"");
        } finally {
            return dataResponse;
        }
    }
}
