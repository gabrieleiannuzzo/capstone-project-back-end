package it.epicode.capstoneProject.model.request;

import it.epicode.capstoneProject.model.enums.RuoloInvito;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InvitoRequest {
    @NotNull(message = "id campionato ?")
    private Integer idCampionato;
    @NotNull(message = "Inserisci l'utente")
    private String ToUserUsername;
    private RuoloInvito ruoloInvito;
    private Integer idScuderia;
}
