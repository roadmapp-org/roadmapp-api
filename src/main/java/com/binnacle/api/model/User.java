package com.binnacle.api.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private String username;
    private String password;
    private String role;
}
