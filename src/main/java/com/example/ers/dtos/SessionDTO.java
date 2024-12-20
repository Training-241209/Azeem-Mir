package com.example.ers.dtos;

import lombok.Data;

@Data
public class SessionDTO {
    private Integer id;
    private String user;
    private Boolean isLoggedIn;
}
