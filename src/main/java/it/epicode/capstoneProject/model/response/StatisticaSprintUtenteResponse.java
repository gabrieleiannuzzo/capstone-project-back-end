package it.epicode.capstoneProject.model.response;

import it.epicode.capstoneProject.model.entity.StatisticaSprintUtente;
import lombok.Data;

@Data
public class StatisticaSprintUtenteResponse {
    private int numeroSprintDisputate;
    private double posizioneMediaGara;
    private int numeroVittorie;
    private int posizionamentiTop3;
    private int numeroRitiri;
    private int numeroPenalita;

    public static StatisticaSprintUtenteResponse createFromStatisticaSprintUtente(StatisticaSprintUtente s){
        StatisticaSprintUtenteResponse response = new StatisticaSprintUtenteResponse();

        response.setNumeroSprintDisputate(s.getNumeroSprintDisputate());
        response.setPosizioneMediaGara(s.getPosizioneMediaGara());
        response.setNumeroVittorie(s.getNumeroVittorie());
        response.setPosizionamentiTop3(s.getPosizionamentiTop3());
        response.setNumeroRitiri(s.getNumeroRitiri());
        response.setNumeroPenalita(s.getNumeroPenalita());

        return response;
    }
}
