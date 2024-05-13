package it.epicode.capstoneProject.model.classes;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SegnalazioneProblema {
    @NotNull(message = "Nome obbligatorio")
    private String nome;
    @NotNull(message = "Email richiesta")
    @Pattern(regexp = "^([\\w\\.-]+)@[a-zA-Z\\d-]+(\\.[a-zA-Z\\d-]+)+$", message = "Formato email non valido")
    private String email;
    @NotNull(message = "Testo obbligatorio")
    private String testo;
}
