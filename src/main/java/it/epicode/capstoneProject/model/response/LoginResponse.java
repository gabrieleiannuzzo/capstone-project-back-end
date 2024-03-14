package it.epicode.capstoneProject.model.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;
    private UtenteResponse utente;

    public LoginResponse(){}

    public LoginResponse(String accessToken, UtenteResponse utente){
        this.accessToken = accessToken;
        this.utente = utente;
    }
}
