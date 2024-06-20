package com.binnacle.api.service;

import com.binnacle.api.entity.ProjectEntity;
import com.binnacle.api.entity.SubtaskEntity;
import com.binnacle.api.entity.TaskEntity;
import com.binnacle.api.repository.contract.IProjectRepository;
import com.binnacle.api.repository.contract.ISubTaskRepository;
import com.binnacle.api.repository.contract.ITaskRepository;
import com.binnacle.api.request.CreateUpdateSubTaskRequest;
import com.binnacle.api.request.CreateUpdateTaskRequest;
import com.binnacle.api.request.DeleteGroupRequest;
import com.binnacle.api.response.PersistResponse;
import com.binnacle.api.service.contract.ISubTaskUseCases;
import com.binnacle.api.service.contract.ITaskUseCases;
import com.binnacle.api.utils.Results;
import com.binnacle.api.utils.Tools;
import com.binnacle.api.utils.errors.ErrorCodes;
import com.binnacle.api.utils.errors.ErrorDescriptions;
import com.binnacle.api.utils.exceptions.ActionNotAllowedException;
import com.binnacle.api.utils.exceptions.AlreadyDefinedException;
import com.binnacle.api.utils.exceptions.AppException;
import com.binnacle.api.utils.exceptions.RecordNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubTaskService implements ISubTaskUseCases {
    private final ITaskRepository taskRepository;
    private final IProjectRepository projectRepository;
    private final ISubTaskRepository subTaskRepository;

    @Override
    public PersistResponse create(CreateUpdateSubTaskRequest request) {
        PersistResponse persistResponse = new PersistResponse();
        SubtaskEntity subtask;
        try {
            ProjectEntity project = projectRepository.findById(request.getProjectId());
            if(project == null)
                throw new RecordNotFoundException(ErrorCodes.RECORD_NOT_FOUND, ErrorDescriptions.RECORD_NOT_FOUND);

            if(!project.getOwner().equals(SecurityContextHolder.getContext().getAuthentication().getName()))
                throw new ActionNotAllowedException(ErrorCodes.CREATION_NOT_ALLOWED, ErrorDescriptions.UPDATE_NOT_ALLOWED);

            // previous validations
            TaskEntity task = taskRepository.findById(request.getTaskId());
            if(project == null)
                throw new RecordNotFoundException(ErrorCodes.RECORD_NOT_FOUND, ErrorDescriptions.RECORD_NOT_FOUND);


            // business logic
            subtask = new SubtaskEntity();
            subtask.setName(request.getName());
            subtask.setTask(task);
            subtask.setActive(true);
            SubtaskEntity savedSubtask = subTaskRepository.save(subtask);

            // return
            persistResponse = new PersistResponse(Results.OK,"",savedSubtask,HttpStatus.OK);

        } catch(AppException e){
            persistResponse = Tools.getBadRequest(e, "");
        } catch(Exception e) {
            persistResponse = Tools.getBadRequest(e, ErrorDescriptions.COULD_NOT_SAVE_PROJECT);
        } finally {
            return persistResponse;
        }
    }


    @Override
    public PersistResponse update(CreateUpdateSubTaskRequest request) {
        PersistResponse persistResponse = new PersistResponse();
        try {
            // previous validations
            SubtaskEntity subtask = subTaskRepository.findById(request.getId());
            if(subtask == null)
                throw new RecordNotFoundException(ErrorCodes.RECORD_NOT_FOUND, ErrorDescriptions.RECORD_NOT_FOUND);

            if(!subtask.getTask().getProject().getOwner().equals(SecurityContextHolder.getContext().getAuthentication().getName()))
                throw new ActionNotAllowedException(ErrorCodes.UPDATE_NOT_ALLOWED, ErrorDescriptions.UPDATE_NOT_ALLOWED);

            for(SubtaskEntity loopSubTask : subtask.getTask().getSubtaskList()) {
                if(loopSubTask.getId() != subtask.getId())
                    if(loopSubTask.getName().equals(request.getName()))
                        throw new AlreadyDefinedException(ErrorCodes.ALREADY_DEFINED, ErrorDescriptions.PROJECT_ALREADY_DEFINED);
            }

            //business logic
            subtask.setName(request.getName());
            SubtaskEntity savedTask = subTaskRepository.save(subtask);

            // return
            persistResponse = new PersistResponse(Results.OK,"",savedTask,HttpStatus.OK);

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
