package it.epicode.capstoneProject.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ScuderiaRequest {
    @NotNull(message = "Il nome della scuderia è obbligatorio")
    @Pattern(regexp = "^(?=.*[a-zA-ZÀ-ÿ])[a-zA-ZÀ-ÿ0-9_.,&@*%!?\\[\\]\\(\\)\\$#\\-+\\s]{4,50}$", message = "Nome scuderia non valido")
    private String nome;
    @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", message = "Codice colore non valido")
    private String codiceColore;

}
