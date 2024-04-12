package it.epicode.capstoneProject.model.response;

import lombok.Data;

import java.util.List;

@Data
public class ProfiloUtenteResponse {
    private UtenteResponse utente;
    private StatisticaUtenteResponse statistiche;
    private StatisticaSprintUtenteResponse statisticheSprint;
    private List<CampionatoResponse> campionati;
    private List<CampionatoResponse> campionatiCreati;
}
