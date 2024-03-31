package it.epicode.capstoneProject.service;

import it.epicode.capstoneProject.model.entity.Utente;
import it.epicode.capstoneProject.model.response.ProfiloUtenteResponse;
import it.epicode.capstoneProject.model.response.UtenteResponse;
import it.epicode.capstoneProject.repository.StatisticaSprintUtenteRepository;
import it.epicode.capstoneProject.repository.StatisticaUtenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    }
}
