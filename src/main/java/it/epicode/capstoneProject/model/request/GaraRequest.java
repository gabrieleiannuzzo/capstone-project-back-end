package it.epicode.capstoneProject.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class GaraRequest {
    // NEL SERVICE VALIDARE CHE I PILOTI INSERITI APPARTENGANO AL CAMPIONATO
    @NotNull(message = "Nome gara obbligatorio")
    @Pattern(regexp = "^(?=.*[a-zA-ZÀ-ÿ])[a-zA-ZÀ-ÿ0-9_.,&@*%!?\\[\\]\\(\\)\\$#\\-+\\s]{4,50}$", message = "Nome gara non valido")
    private String nome;
    @NotNull(message = "Gara sprint ?")
    private Boolean sprint;
}
