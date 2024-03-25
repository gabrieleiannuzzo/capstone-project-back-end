package it.epicode.capstoneProject.model.request;

import lombok.Data;

@Data
public class LoginRequest {
    // DA VALIDARE
    private String user;
    private String password;
}
