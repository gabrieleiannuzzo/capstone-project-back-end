package it.epicode.capstoneProject.model.response;

import it.epicode.capstoneProject.model.classes.Utility;
import it.epicode.capstoneProject.model.entity.Utente;
import lombok.Data;

@Data
public class UtenteResponse {
    private int id;
    private String username;
    private String email;
    private boolean verificato;
    private String dataCreazione;

    public static UtenteResponse createFromUtente(Utente utente){
        UtenteResponse utenteResponse = new UtenteResponse();
        utenteResponse.setId(utente.getId());
        utenteResponse.setUsername(utente.getUsername());
        utenteResponse.setEmail(utente.getEmail());
        utenteResponse.setVerificato(utenteResponse.isVerificato());
        utenteResponse.setDataCreazione(Utility.convertDate(utente.getDataCreazione()));
        return utenteResponse;
    }
}
