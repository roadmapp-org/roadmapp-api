package com.binnacle.api.response.task;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskResponse {

        private int id;
        private String name;
        private int project_id;
        private boolean active;

        public TaskResponse(int id, String name, int project_id, boolean active) {
            this.id = id;
            this.name = name;
            this.project_id = project_id;
            this.active = active;
        }

}
