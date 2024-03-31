package it.epicode.capstoneProject.service;

import it.epicode.capstoneProject.model.entity.Campionato;
import it.epicode.capstoneProject.model.entity.Pilota;
import it.epicode.capstoneProject.model.entity.Utente;
import it.epicode.capstoneProject.model.response.*;
import it.epicode.capstoneProject.repository.StatisticaSprintUtenteRepository;
import it.epicode.capstoneProject.repository.StatisticaUtenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfiloUtenteResponseService {
    private final UtenteService utenteService;
    private final CampionatoService campionatoService;
    private final StatisticaUtenteRepository statisticaUtenteRepository;
    private final StatisticaSprintUtenteRepository statisticaSprintUtenteRepository;
    private final PilotaService pilotaService;

    public ProfiloUtenteResponse getByUsername(String username){
        ProfiloUtenteResponse response = new ProfiloUtenteResponse();

        Utente utente = utenteService.getByUsername(username);
        UtenteResponse u = UtenteResponse.createFromUtente(utente);
        StatisticaUtenteResponse sur = StatisticaUtenteResponse.createFromStatisticaUtente(statisticaUtenteRepository.getByUserId(u.getId()));
        StatisticaSprintUtenteResponse ssur = StatisticaSprintUtenteResponse.createFromStatisticaSprintUtente(statisticaSprintUtenteRepository.getByUserId(u.getId()));
        List<Campionato> campionatiCreati = campionatoService.getByCreatorUsername(username);

        List<CampionatoResponse> campionatiResponseCreati = new ArrayList<>();
        for (Campionato c : campionatiCreati) campionatiResponseCreati.add(CampionatoResponse.createByCampionato(c));

        List<Pilota> pilotiFromUtente = pilotaService.getByUtente(utente);
        List<CampionatoResponse> campionatiResponse = new ArrayList<>();

        for (Pilota p : pilotiFromUtente) campionatiResponse.add(CampionatoResponse.createByCampionato(p.getCampionato()));

        response.setUtente(u);
        response.setStatistiche(sur);
        response.setStatisticheSprint(ssur);
        response.setCampionatiCreati(campionatiResponseCreati);
        response.setCampionati(campionatiResponse);

        return response;
    }
}
