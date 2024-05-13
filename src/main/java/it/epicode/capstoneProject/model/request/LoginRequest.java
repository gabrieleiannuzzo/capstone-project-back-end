package it.epicode.capstoneProject.model.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String user;
    private String password;
}
