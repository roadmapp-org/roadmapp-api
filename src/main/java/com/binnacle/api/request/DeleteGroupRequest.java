package com.binnacle.api.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class DeleteGroupRequest {
    @NotNull
    private Integer id;
}
