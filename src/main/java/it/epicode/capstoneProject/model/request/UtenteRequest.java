package it.epicode.capstoneProject.model.request;

import lombok.Data;

@Data
public class UtenteRequest {
    // DA VALIDARE
    private String username;
    private String email;
    private String password;
}
