package it.epicode.capstoneProject.model.response;

import it.epicode.capstoneProject.model.classes.Utility;
import it.epicode.capstoneProject.model.entity.Gara;
import jdk.jshell.execution.Util;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Data
public class GaraResponse {
    private int id;
    private String nome;
    private int numeroGara;
    private boolean sprint;
    private List<Object> sprintQuali;
    private List<Object> sprintRace;
    private List<Object> sprintRetired;
    private List<Object> sprintPenalties;
    private List<Object> quali;
    private List<Object> race;
    private List<Object> retired;
    private List<Object> penalties;
    private int idPilotaGiroVeloce;

    public static List<GaraResponse> createFromGaraList(List<Gara> gare){
        List<GaraResponse> gareResponse = new ArrayList<>();

        for (Gara gara : gare) {
            GaraResponse response = new GaraResponse();
            response.setId(gara.getId());
            response.setNome(gara.getNome());
            response.setNumeroGara(gara.getNumeroGara());
            response.setSprint(gara.isSprint());
            if (gara.getFastestLapDriver() != null) response.setIdPilotaGiroVeloce(gara.getFastestLapDriver().getId());

            response.setSprintQuali(Utility.jsonParseList(gara.getSprintQuali()));
            response.setSprintRace(Utility.jsonParseList(gara.getSprintRace()));
            response.setSprintRetired(Utility.jsonParseList(gara.getSprintRetired()));
            response.setSprintPenalties(Utility.jsonParseList(gara.getSprintPenalties()));
            response.setQuali(Utility.jsonParseList(gara.getQuali()));
            response.setRace(Utility.jsonParseList(gara.getRace()));
            response.setRetired(Utility.jsonParseList(gara.getRetired()));
            response.setPenalties(Utility.jsonParseList(gara.getPenalties()));

            gareResponse.add(response);
        }

        return gareResponse;
    }
}
