package com.binnacle.api.service;

import com.binnacle.api.entity.ProjectEntity;
import com.binnacle.api.repository.contract.IProjectRepository;
import com.binnacle.api.request.CreateUpdateProjectRequest;
import com.binnacle.api.request.DeleteGroupRequest;
import com.binnacle.api.response.DataResponse;
import com.binnacle.api.response.PersistResponse;
import com.binnacle.api.response.project.ProjectResponse;
import com.binnacle.api.service.contract.IProjectUseCases;
import com.binnacle.api.utils.Results;
import com.binnacle.api.utils.Tools;
import com.binnacle.api.utils.errors.ErrorCodes;
import com.binnacle.api.utils.errors.ErrorDescriptions;
import com.binnacle.api.utils.exceptions.AlreadyDefinedException;
import com.binnacle.api.utils.exceptions.ErrorWhenRetreivingDataException;
import com.binnacle.api.utils.exceptions.RecordNotFoundException;
import com.binnacle.api.utils.exceptions.ActionNotAllowedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService implements IProjectUseCases {
    private final IProjectRepository projectRepository;

    @Override
    public PersistResponse create(CreateUpdateProjectRequest request) {
        PersistResponse persistResponse = new PersistResponse();
        try {
            // previous validations
            ProjectEntity project = projectRepository.findByProjectName(request.getName());

            if(project != null)
                if(project.getOwner().equals(SecurityContextHolder.getContext().getAuthentication().getName()))
                    throw new AlreadyDefinedException(ErrorCodes.ALREADY_DEFINED, ErrorDescriptions.PROJECT_ALREADY_DEFINED);

            // business logic
            project = new ProjectEntity();
            project.setName(request.getName());
            project.setActive(true);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            project.setOwner(authentication.getName());
            ProjectEntity savedProject = projectRepository.save(project);

            // return
            persistResponse = new PersistResponse(Results.OK,"",savedProject,HttpStatus.OK);

        } catch(AlreadyDefinedException e){
            persistResponse = Tools.getBadRequest(e, "");
        } catch(Exception e) {
            persistResponse = Tools.getBadRequest(e, ErrorDescriptions.COULD_NOT_SAVE_PROJECT);
        } finally {
            return persistResponse;
        }
    }

    @Override
    public PersistResponse update(CreateUpdateProjectRequest request) {
        PersistResponse persistResponse = new PersistResponse();
        try {
            // previous validations
            ProjectEntity project = projectRepository.findById(request.getId());
            if(project == null)
                throw new RecordNotFoundException(ErrorCodes.RECORD_NOT_FOUND, ErrorDescriptions.RECORD_NOT_FOUND);
            if(!project.getOwner().equals(SecurityContextHolder.getContext().getAuthentication().getName()))
                throw new ActionNotAllowedException(ErrorCodes.UPDATE_NOT_ALLOWED, ErrorDescriptions.UPDATE_NOT_ALLOWED);

            // TODO: validate that the name we are trying to set does not exist

            //business logic
            project.setName(request.getName());
            project.setActive(request.isActive());
            ProjectEntity savedProject = projectRepository.save(project);

            // return
            persistResponse = new PersistResponse(Results.OK,"",savedProject,HttpStatus.OK);

        } catch(RecordNotFoundException e) {
            persistResponse = Tools.getBadRequest(e,"");
        } catch(ActionNotAllowedException e) {
            persistResponse = Tools.getUnauthorized(e,"");
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
            ProjectEntity project = projectRepository.findById(request.getId());
            if(project == null)
                throw new RecordNotFoundException(ErrorCodes.RECORD_NOT_FOUND, ErrorDescriptions.RECORD_NOT_FOUND);
            if(!project.getOwner().equals(SecurityContextHolder.getContext().getAuthentication().getName()))
                throw new ActionNotAllowedException(ErrorCodes.DELETION_NOT_ALLOWED, ErrorDescriptions.DELETION_NOT_ALLOWED);

            //business logic
            projectRepository.delete(project);

            // return
            persistResponse = new PersistResponse(Results.OK,"",project,HttpStatus.OK);

        } catch(RecordNotFoundException e) {
            persistResponse = Tools.getBadRequest(e,"");
        } catch(ActionNotAllowedException e) {
            persistResponse = Tools.getUnauthorized(e,"");
        } catch(Exception e){
            persistResponse = Tools.getBadRequest(e,ErrorDescriptions.COULD_NOT_SAVE_PROJECT);
        }
        finally {
            return persistResponse;
        }
    }

    @Override
    public DataResponse getMyProjects() {
        DataResponse dataResponse = new DataResponse();
        List<ProjectResponse> projectList = new ArrayList<ProjectResponse>();
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            projectList = projectRepository.getAllByOwner(name);
            if(projectList == null)
                throw new ErrorWhenRetreivingDataException(ErrorCodes.ERROR_WHEN_RETREIVING_DATA, ErrorDescriptions.ERROR_WHEN_RETREIVING_DATA);

            dataResponse = new DataResponse(Results.OK,"",projectList,HttpStatus.OK);

        } catch (Exception e) {
            dataResponse = Tools.getDataResponseError(e,"");
        } finally {
            return dataResponse;
        }
    }
}
