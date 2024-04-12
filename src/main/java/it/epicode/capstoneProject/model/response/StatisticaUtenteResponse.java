package it.epicode.capstoneProject.model.response;

import it.epicode.capstoneProject.model.entity.StatisticaUtente;
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

    public static StatisticaUtenteResponse createFromStatisticaUtente(StatisticaUtente s){
        StatisticaUtenteResponse response = new StatisticaUtenteResponse();

        response.setNumeroGareDisputate(s.getNumeroGareDisputate());
        response.setPosizioneMediaGara(s.getPosizioneMediaGara());
        response.setPosizioneMediaQualifica(s.getPosizioneMediaQualifica());
        response.setNumeroVittorie(s.getNumeroVittorie());
        response.setNumeroPolePositions(s.getNumeroPolePositions());
        response.setPosizionamentiTop3(s.getPosizionamentiTop3());
        response.setPosizionamentiTop10(s.getPosizionamentiTop10());
        response.setNumeroRitiri(s.getNumeroRitiri());
        response.setNumeroPenalita(s.getNumeroPenalita());

        return response;
    }
}
