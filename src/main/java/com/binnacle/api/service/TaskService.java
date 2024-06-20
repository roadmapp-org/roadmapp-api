package com.binnacle.api.service;

import com.binnacle.api.entity.ProjectEntity;
import com.binnacle.api.entity.TaskEntity;
import com.binnacle.api.repository.contract.IProjectRepository;
import com.binnacle.api.repository.contract.ITaskRepository;
import com.binnacle.api.request.CreateUpdateTaskRequest;
import com.binnacle.api.request.DeleteGroupRequest;
import com.binnacle.api.response.DataResponse;
import com.binnacle.api.response.PersistResponse;
import com.binnacle.api.response.task.TaskResponse;
import com.binnacle.api.service.contract.ITaskUseCases;
import com.binnacle.api.utils.Results;
import com.binnacle.api.utils.Tools;
import com.binnacle.api.utils.errors.ErrorCodes;
import com.binnacle.api.utils.errors.ErrorDescriptions;
import com.binnacle.api.utils.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService implements ITaskUseCases {
    private final ITaskRepository taskRepository;
    private final IProjectRepository projectRepository;

    @Override
    public PersistResponse create(CreateUpdateTaskRequest request) {
        PersistResponse persistResponse = new PersistResponse();
        try {
            ProjectEntity project = projectRepository.findById(request.getProjectId());
            if(project == null)
                throw new RecordNotFoundException(ErrorCodes.RECORD_NOT_FOUND, ErrorDescriptions.RECORD_NOT_FOUND);

            if(!project.getOwner().equals(SecurityContextHolder.getContext().getAuthentication().getName()))
                throw new ActionNotAllowedException(ErrorCodes.CREATION_NOT_ALLOWED, ErrorDescriptions.UPDATE_NOT_ALLOWED);

            // previous validations
            TaskEntity task = taskRepository.findByTaskName(request.getName());
            if(task != null)
                    throw new AlreadyDefinedException(ErrorCodes.ALREADY_DEFINED, ErrorDescriptions.PROJECT_ALREADY_DEFINED);

            // business logic
            task = new TaskEntity();
            task.setName(request.getName());
            task.setProject(project);
            task.setActive(true);
            TaskEntity savedTask = taskRepository.save(task);

            TaskResponse response = new TaskResponse(
                    savedTask.getId(),
                    savedTask.getName(),
                    savedTask.getProject().getId(),
                    savedTask.isActive()
            );

            // return
            persistResponse = new PersistResponse(Results.OK,"",response,HttpStatus.OK);

        } catch(AppException e){
            persistResponse = Tools.getBadRequest(e, "");
        } catch(Exception e) {
            persistResponse = Tools.getBadRequest(e, ErrorDescriptions.COULD_NOT_SAVE_PROJECT);
        } finally {
            return persistResponse;
        }
    }


    @Override
    public PersistResponse update(CreateUpdateTaskRequest request) {
        PersistResponse persistResponse = new PersistResponse();
        try {
            // previous validations
            TaskEntity task = taskRepository.findById(request.getId());
            if(task == null)
                throw new RecordNotFoundException(ErrorCodes.RECORD_NOT_FOUND, ErrorDescriptions.RECORD_NOT_FOUND);

            if(!task.getProject().getOwner().equals(SecurityContextHolder.getContext().getAuthentication().getName()))
                throw new ActionNotAllowedException(ErrorCodes.UPDATE_NOT_ALLOWED, ErrorDescriptions.UPDATE_NOT_ALLOWED);

            for(TaskEntity loopTask : task.getProject().getTaskList()) {
                if(loopTask.getId() != task.getId())
                    if(loopTask.getName().equals(request.getName()))
                        throw new AlreadyDefinedException(ErrorCodes.ALREADY_DEFINED, ErrorDescriptions.PROJECT_ALREADY_DEFINED);
            }

            //business logic
            task.setName(request.getName());
            task.setActive(request.isActive());
            TaskEntity savedTask = taskRepository.save(task);

            TaskResponse response = new TaskResponse(
                    savedTask.getId(),
                    savedTask.getName(),
                    savedTask.getProject().getId(),
                    savedTask.isActive()
            );

            // return
            persistResponse = new PersistResponse(Results.OK,"",response,HttpStatus.OK);

        } catch(AppException e) {
            persistResponse = Tools.getBadRequest(e,"");
        } catch (Exception e) {
            persistResponse = Tools.getBadRequest(e,ErrorDescriptions.COULD_NOT_SAVE_PROJECT);
        } finally {
            return persistResponse;
        }
    }


    @Override
    public PersistResponse delete(DeleteGroupRequest request) {
        PersistResponse persistResponse = new PersistResponse();
        try {
            // previous validations
            TaskEntity task = taskRepository.findById(request.getId());
            if(task == null)
                throw new RecordNotFoundException(ErrorCodes.RECORD_NOT_FOUND, ErrorDescriptions.RECORD_NOT_FOUND);
            if(!task.getProject().getOwner().equals(SecurityContextHolder.getContext().getAuthentication().getName()))
                throw new ActionNotAllowedException(ErrorCodes.DELETION_NOT_ALLOWED, ErrorDescriptions.DELETION_NOT_ALLOWED);

            //business logic
            taskRepository.delete(task);

            // return
            persistResponse = new PersistResponse(Results.OK,"",task,HttpStatus.OK);

        } catch(AppException e) {
            persistResponse = Tools.getUnauthorized(e,"");
        } catch(Exception e){
            persistResponse = Tools.getBadRequest(e,ErrorDescriptions.COULD_NOT_SAVE_PROJECT);
        }
        finally {
            return persistResponse;
        }
    }



}
