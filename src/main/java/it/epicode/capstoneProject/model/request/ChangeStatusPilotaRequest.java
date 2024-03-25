package it.epicode.capstoneProject.model.request;

import it.epicode.capstoneProject.model.enums.ActionStatusPilota;
import lombok.Data;

@Data
public class ChangeStatusPilotaRequest {
    private ActionStatusPilota action;
    private int idNuovaScuderia;
}
