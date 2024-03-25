package it.epicode.capstoneProject.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import it.epicode.capstoneProject.model.entity.Invito;
import it.epicode.capstoneProject.model.enums.RuoloInvito;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InvitoResponse {
    private int id;
    private CampionatoResponse campionato;
    private UtenteResponse fromUser;
    private UtenteResponse toUser;
    private Boolean accepted;
    private RuoloInvito ruolo;
    private ScuderiaResponse scuderia;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    public static InvitoResponse createFromInvito(Invito invito){
        InvitoResponse response = new InvitoResponse();
        response.setId(invito.getId());
        response.setCampionato(CampionatoResponse.createByCampionato(invito.getCampionato()));
        response.setFromUser(UtenteResponse.createFromUtente(invito.getFromUser()));
        response.setToUser(UtenteResponse.createFromUtente(invito.getToUser()));
        response.setAccepted(invito.getAccepted());
        response.setRuolo(invito.getRuoloInvito());
        if (invito.getRuoloInvito() == RuoloInvito.PILOTA_TITOLARE) response.setScuderia(ScuderiaResponse.createFromScuderia(invito.getScuderia()));
        response.setCreatedAt(invito.getCreatedAt());
        return response;
    }
}
