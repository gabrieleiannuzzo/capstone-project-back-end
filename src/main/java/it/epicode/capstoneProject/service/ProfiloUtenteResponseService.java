package it.epicode.capstoneProject.service;

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

    public ProfiloUtenteResponse getByUsername(String username){
        ProfiloUtenteResponse response = new ProfiloUtenteResponse();

        UtenteResponse u = UtenteResponse.createFromUtente(utenteService.getByUsername(username));
        StatisticaUtenteResponse sur = StatisticaUtenteResponse.createFromStatisticaUtente(statisticaUtenteRepository.getByUserId(u.getId()));
        StatisticaSprintUtenteResponse ssur = StatisticaSprintUtenteResponse.createFromStatisticaSprintUtente(statisticaSprintUtenteRepository.getByUserId(u.getId()));
        List<CampionatoResponse> campionatiCreati = campionatoService.getByCreatorUsername(username);

        response.setUtente(u);
        response.setStatistiche(sur);
        response.setStatisticheSprint(ssur);
        response.setCampionatiCreati(campionatiCreati);
        response.setCampionati(new ArrayList<>());

        return response;
    }
}
