package it.epicode.capstoneProject.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class AggiornaGaraRequest {
    @NotNull(message = "Quale gara vuoi modificare?")
    private Integer idGara;
    private List<Integer> sprintQuali;
    private List<Integer> sprintRace;
    private List<Integer> sprintRetired;
    private List<Integer> sprintPenalties;
    private List<Integer> quali;
    @NotNull(message = "Inserire risultati gara")
    private List<Integer> race;
    @NotNull(message = "I ritirati devono essere un array")
    private List<Integer> retired;
    @NotNull(message = "I penalizzati devono essere un array")
    private List<Integer> penalties;
    private Integer idPilotaFastestLap;
    private List<WildCardPerGaraRequest> wildCards;
}
