package it.epicode.capstoneProject.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class CampionatoRequest {
    // CONTROLLO SU POSIZIONE GIRO VELOCE SE SELEZIONATO
    // CONTROLLO SU NUMERO DI SCUDERIE (10) E NUMERO DI GARE (2 - 24)
    // CONTROLLO SU SCUDERIE CON NOMI DUPLICATI
    @NotNull(message = "Nome campionato obbligatorio")
    @Pattern(regexp = "^(?=.*[a-zA-ZÀ-ÿ])[a-zA-ZÀ-ÿ0-9_.,&@*%!?\\[\\]\\(\\)\\$#\\-+\\s]{4,50}$", message = "Nome campionato non valido")
    private String nome;
    @NotNull(message = "Piloti reali ?")
    private Boolean realDrivers;
    @NotNull(message = "Formato sprint ?")
    private Boolean independentSprint;
    @NotNull(message = "Salvare le qualifiche ?")
    private Boolean saveQuali;
    @NotNull(message = "Punto pole position ?")
    private Boolean polePoint;
    @NotNull(message = "Punto giro veloce ?")
    private Boolean fastestLapPoint;
    @Min(value = 1, message = "Posizione minima giro veloce = 1")
    @Max(value = 20, message = "Posizione massima giro veloce = 20")
    private Integer minFastestLapPosition;
    @NotNull(message = "Punteggi obbligatori")
    private PunteggioRequest punteggi;
    @NotNull(message = "Scuderie obbligatorie")
    private List<ScuderiaRequest> scuderie;
    @NotNull(message = "Gare obbligatorie")
    private List<GaraRequest> gare;
}
