package it.epicode.capstoneProject.model.response;

import lombok.Data;

@Data
public class StatisticaUtenteResponse {
    private int numeroGareDisputate;
    private double posizioneMediaGara;
    private double posizioneMediaQualifica;
    private int numeroVittorie;
    private int numeroPolePositions;
    private int posizionamentiTop3;
    private int posizionamentiTop10;
    private int numeroRitiri;
    private int numeroPenalita;
}
