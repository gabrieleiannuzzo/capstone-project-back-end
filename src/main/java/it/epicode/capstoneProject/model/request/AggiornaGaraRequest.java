package it.epicode.capstoneProject.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class AggiornaGaraRequest {
    @NotNull(message = "Quale gara vuoi modificare?")
    private Integer idGara;
    @NotNull(message = "Inserire risultati qualifica sprint")
    private List<Integer> sprintQuali;
    @NotNull(message = "Inserire risultati gara sprint")
    private List<Integer> sprintRace;
    @NotNull(message = "Inserire ritirati gara sprint")
    private List<Integer> sprintRetired;
    @NotNull(message = "Inserire penalizzati gara sprint")
    private List<Integer> sprintPenalties;
    @NotNull(message = "Inserire risultati qualifiche")
    private List<Integer> quali;
    @NotNull(message = "Inserire risultati gara")
    private List<Integer> race;
    @NotNull(message = "Inserire ritirati gara")
    private List<Integer> retired;
    @NotNull(message = "Inserire penalizzati gara")
    private List<Integer> penalties;
    private Integer idPilotaFastestLap;
    private List<WildCardPerGaraRequest> wildCards;
}
