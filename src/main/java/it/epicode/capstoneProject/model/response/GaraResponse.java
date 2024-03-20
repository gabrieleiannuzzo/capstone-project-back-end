package it.epicode.capstoneProject.model.response;

import it.epicode.capstoneProject.model.classes.Utility;
import it.epicode.capstoneProject.model.entity.Gara;
import jdk.jshell.execution.Util;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GaraResponse {
    private int id;
    private String nome;
    private int numeroGara;
    private boolean sprint;
    private List<Integer> sprintQuali;
    private List<Integer> sprintRace;
    private List<Integer> sprintRetired;
    private List<Integer> sprintPenalties;
    private List<Integer> quali;
    private List<Integer> race;
    private List<Integer> retired;
    private List<Integer> penalties;
    private int idPilotaGiroVeloce;

    public static List<GaraResponse> createFromGaraList(List<Gara> gare){
        List<GaraResponse> gareResponse = new ArrayList<>();

        for (Gara gara : gare) {
            GaraResponse response = new GaraResponse();
            response.setId(gara.getId());
            response.setNome(gara.getNome());
            response.setNumeroGara(gara.getNumeroGara());
            response.setSprint(gara.isSprint());
            response.setIdPilotaGiroVeloce(gara.getFastestLapDriver().getId());

            if (gara.getSprintQuali() != null) response.setSprintQuali(Utility.jsonParseList(gara.getSprintQuali()));
            if (gara.getSprintRace() != null) response.setSprintRace(Utility.jsonParseList(gara.getSprintRace()));
            if (gara.getSprintRetired() != null) response.setSprintRetired(Utility.jsonParseList(gara.getSprintRetired()));
            if (gara.getSprintPenalties() != null) response.setSprintPenalties(Utility.jsonParseList(gara.getSprintPenalties()));
            if (gara.getQuali() != null) response.setQuali(Utility.jsonParseList(gara.getQuali()));
            if (gara.getRace() != null) response.setRace(Utility.jsonParseList(gara.getRace()));
            if (gara.getRetired() != null) response.setRetired(Utility.jsonParseList(gara.getRetired()));
            if (gara.getPenalties() != null) response.setPenalties(Utility.jsonParseList(gara.getPenalties()));

            gareResponse.add(response);
        }

        return gareResponse;
    }
}
