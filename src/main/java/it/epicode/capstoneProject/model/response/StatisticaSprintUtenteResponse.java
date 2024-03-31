package it.epicode.capstoneProject.model.response;

import lombok.Data;

@Data
public class StatisticaSprintUtenteResponse {
    private int numeroSprintDisputate;
    private double posizioneMediaGara;
    private int numeroVittorie;
    private int posizionamentiTop3;
    private int numeroRitiri;
    private int numeroPenalita;
}
