package it.epicode.capstoneProject.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ManageInvitoRequest {
    @NotNull(message = "Devi accettare o rifiutare l'invito")
    private Boolean accepted;
}
