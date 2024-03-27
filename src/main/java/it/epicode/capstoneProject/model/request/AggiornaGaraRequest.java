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
    private List<Integer> race;
    private List<Integer> retired;
    private List<Integer> penalties;
    private Integer idPilotaFastestLap;
    private List<WildCardPerGaraRequest> wildCards;
}
