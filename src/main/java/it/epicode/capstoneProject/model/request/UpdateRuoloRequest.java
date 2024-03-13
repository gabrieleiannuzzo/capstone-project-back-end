package it.epicode.capstoneProject.model.request;

import it.epicode.capstoneProject.model.enums.Ruolo;
import lombok.Data;

@Data
public class UpdateRuoloRequest {
    private Ruolo newRuolo;
}
