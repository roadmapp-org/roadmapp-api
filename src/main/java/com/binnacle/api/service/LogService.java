package com.binnacle.api.service;

import com.binnacle.api.entity.LogEntity;
import com.binnacle.api.entity.ProjectEntity;
import com.binnacle.api.entity.SubtaskEntity;
import com.binnacle.api.entity.TaskEntity;
import com.binnacle.api.repository.contract.ILogRepository;
import com.binnacle.api.repository.contract.IProjectRepository;
import com.binnacle.api.repository.contract.ISubTaskRepository;
import com.binnacle.api.repository.contract.ITaskRepository;
import com.binnacle.api.request.CreateUpdateLogRequest;
import com.binnacle.api.response.DataResponse;
import com.binnacle.api.response.PersistResponse;
import com.binnacle.api.response.log.LogResponse;
import com.binnacle.api.response.project.ProjectResponse;
import com.binnacle.api.service.contract.ILogUseCases;
import com.binnacle.api.utils.Constants;
import com.binnacle.api.utils.Results;
import com.binnacle.api.utils.Tools;
import com.binnacle.api.utils.errors.ErrorCodes;
import com.binnacle.api.utils.errors.ErrorDescriptions;
import com.binnacle.api.utils.exceptions.ActionNotAllowedException;
import com.binnacle.api.utils.exceptions.AppException;
import com.binnacle.api.utils.exceptions.ErrorWhenRetreivingDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LogService implements ILogUseCases {
    private final ILogRepository logRepository;
    private final IProjectRepository projectRepository;
    private final ITaskRepository taskRepository;
    private final ISubTaskRepository subTaskRepository;


    @Override
    public PersistResponse create(CreateUpdateLogRequest request) {
        // previous validations
        PersistResponse persistResponse = new PersistResponse();
        try {
            //the project must not be empty and must be owned by the user who requests
            ProjectEntity project = null;
            if (request.getProjectId() != 0)
                project = projectRepository.findById(request.getProjectId());

            if (project == null || !project.getOwner().equals(SecurityContextHolder.getContext().getAuthentication().getName()))
                throw new ActionNotAllowedException(ErrorCodes.CREATION_NOT_ALLOWED, ErrorDescriptions.INVALID_PROJECT);

            // the task can be empty. if not empty, must by owned by the user who requests and related to the given project
            TaskEntity task = null;
            if(request.getTaskId() != 0){
                task = taskRepository.findById(request.getTaskId());
                if(task == null)
                    throw new ActionNotAllowedException(ErrorCodes.CREATION_NOT_ALLOWED, ErrorDescriptions.TASK_NOT_FOUND);
            }
            if(task != null && !task.getProject().getOwner().equals(SecurityContextHolder.getContext().getAuthentication().getName()))
                throw new ActionNotAllowedException(ErrorCodes.CREATION_NOT_ALLOWED, ErrorDescriptions.INVALID_TASK);
            if(!isTaskInProject(task,project))
                throw new ActionNotAllowedException(ErrorCodes.CREATION_NOT_ALLOWED, ErrorDescriptions.TASK_RELATION_ERROR);

            // the subtask can be empty. if not empty, must by owned by the user who requests and related to the given task
            SubtaskEntity subtask = null;
            if(request.getSubtaskId() != 0){
                subtask = subTaskRepository.findById(request.getSubtaskId());
                if(subtask == null)
                    throw new ActionNotAllowedException(ErrorCodes.CREATION_NOT_ALLOWED, ErrorDescriptions.SUBTASK_NOT_FOUND);
            }

            if(subtask != null && !subtask.getTask().getProject().getOwner().equals(SecurityContextHolder.getContext().getAuthentication().getName()))
                throw new ActionNotAllowedException(ErrorCodes.CREATION_NOT_ALLOWED, ErrorDescriptions.INVALID_SUBTASK);
            if(!isSubtaskInTask(subtask,task))
                throw new ActionNotAllowedException(ErrorCodes.CREATION_NOT_ALLOWED, ErrorDescriptions.SUBTASK_RELATION_ERROR);

            // business logic
            LogEntity log = new LogEntity();
            log.setProject(project);
            log.setTask(task);
            log.setSubtask(subtask);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            log.setDate(LocalDateTime.now().format(formatter));
            //log.setDate(LocalDateTime.now());
            log.setLog(request.getLog());
            log.setActive(true);

            LogEntity savedLog = logRepository.save(log);
            LogResponse response = LogResponse.builder()
                    .id(savedLog.getId())
                    .active(log.isActive())
                    .project_id(log.getProject().getId())
                    .task_id(log.getTask() != null ? log.getTask().getId() : 0)
                    .subtask_id(log.getSubtask() != null ? log.getSubtask().getId() : 0)
                    .log(log.getLog())
                    .date(log.getDate())
                    .build();

            // response
            persistResponse = new PersistResponse(Results.OK,"",response, HttpStatus.OK);

        } catch(AppException e){
            persistResponse = Tools.getBadRequest(e, ErrorDescriptions.COULD_NOT_SAVE_PROJECT);
        } catch(Exception e) {
            persistResponse = Tools.getBadRequest(e, ErrorDescriptions.COULD_NOT_SAVE_PROJECT);
        } finally {
            return persistResponse;
        }

    }

    @Override
    public DataResponse getLatestByOwner() {
        DataResponse dataResponse = new DataResponse();

        List<LogResponse> logList;

        String owner = SecurityContextHolder.getContext().getAuthentication().getName();
        try {

            logList = logRepository.findTop10ByOwnerOrderByDateDesc(owner);
            if(logList == null)
                throw new ErrorWhenRetreivingDataException(ErrorCodes.ERROR_WHEN_RETREIVING_DATA, ErrorDescriptions.ERROR_WHEN_RETREIVING_DATA);

            dataResponse = new DataResponse(Results.OK,"",logList, HttpStatus.OK);

        } catch (Exception e) {
            dataResponse = Tools.getDataResponseError(e, "");
        } finally {
            return dataResponse;
        }
    }

    @Override
    public DataResponse getFiltered(int projectId, int taskId, int subtaskId, int alreadySent) {
        DataResponse dataResponse = new DataResponse();
        List<LogResponse> logList = new ArrayList<>();
        String owner = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            int limit = Constants.FILTERED_QUERY_LIMIT, offset = 0;
            int pages = alreadySent / Constants.FILTERED_QUERY_LIMIT;
            int restToCompletePage = Constants.FILTERED_QUERY_LIMIT - (alreadySent % Constants.FILTERED_QUERY_LIMIT);
            if(restToCompletePage == Constants.FILTERED_QUERY_LIMIT) {
                offset = pages * Constants.FILTERED_QUERY_LIMIT;
            } else {
                offset = pages * Constants.FILTERED_QUERY_LIMIT + alreadySent % Constants.FILTERED_QUERY_LIMIT;
            }

            logList = logRepository.findFilteredAndPaged(owner, projectId, taskId, subtaskId, limit, offset);
            if(logList == null)
                throw new ErrorWhenRetreivingDataException(ErrorCodes.ERROR_WHEN_RETREIVING_DATA, ErrorDescriptions.ERROR_WHEN_RETREIVING_DATA);

            dataResponse = new DataResponse(Results.OK,"",logList, HttpStatus.OK);

        } catch (Exception e) {
            dataResponse = Tools.getDataResponseError(e, "");
        }

        return dataResponse;
    }

    private boolean isTaskInProject(TaskEntity task, ProjectEntity project) {
        if(task == null) return true;
        for (TaskEntity taskLoop : project.getTaskList()) {
            if (taskLoop.getId() == task.getId())
                return true;
        }
        return false;
    }

    private boolean isSubtaskInTask(SubtaskEntity subtask, TaskEntity task) {
        if(subtask == null) return true;
        for(SubtaskEntity subtaskLoop : task.getSubtaskList())
        {
            if(subtaskLoop.getTask().getId() == task.getId())
                return true;
        }
        return false;
    }

}
