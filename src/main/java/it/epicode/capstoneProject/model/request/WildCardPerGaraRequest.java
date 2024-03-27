package it.epicode.capstoneProject.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WildCardPerGaraRequest {
    @NotNull(message = "Inserisci la wild card")
    private Integer idWildCard;
    @NotNull(message = "Inserisci la scuderia")
    private Integer idScuderia;
}
