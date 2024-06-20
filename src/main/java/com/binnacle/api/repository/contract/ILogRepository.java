package com.binnacle.api.repository.contract;

import com.binnacle.api.entity.LogEntity;
import com.binnacle.api.model.Log;
import com.binnacle.api.response.log.LogResponse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILogRepository {
    LogEntity save (LogEntity log);
    List<LogResponse> getLatestByOwner(String owner);
    List<LogResponse> getLatestByProject(int project, String owner);
    List<LogResponse> getLatestByTask(int task, String owner);
    List<LogResponse> getLatestBySubTask(int subtask, String owner);
    List<LogResponse> findTop10ByOwnerOrderByDateDesc(@Param("owner") String owner);
    List<LogResponse> findFilteredAndPaged(String owner, int projectId, int taskId, int subtaskId, int limit, int offset);
}
