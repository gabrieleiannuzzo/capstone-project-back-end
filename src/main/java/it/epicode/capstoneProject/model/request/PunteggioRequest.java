package it.epicode.capstoneProject.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PunteggioRequest {
    // AGGIUNGERE CONTROLLO CHE DEVONO ESSERE 20 PUNTEGGI
    // AGGIUNGERE CONTROLLO PUNTEGGI NON NULLI
    // AGGIUNGERE CONTROLLO PUNTEGGI DECRESCENTI
    // AGGIUNGERE CONTROLLO SU PUNTEGGI MASSIMI DI SPRINT E GARA (50 E 25) E MINIMO (0)
    @NotNull(message = "Punteggi sprint obbligatori")
    private List<Integer> sprintPoints;
    @NotNull(message = "Punteggi gare obbligatori")
    private List<Integer> racePoints;
}
