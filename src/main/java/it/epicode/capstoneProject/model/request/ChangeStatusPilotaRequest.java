package it.epicode.capstoneProject.model.request;

import it.epicode.capstoneProject.model.enums.ActionStatusPilota;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangeStatusPilotaRequest {
    @NotNull(message = "Inserisci il pilota che vuoi gestire")
    private Integer idPilota;
    @NotNull(message = "Inserisci il tipo di cambiamento che vuoi fare")
    private ActionStatusPilota action;
    private Integer idNuovaScuderia;
}
