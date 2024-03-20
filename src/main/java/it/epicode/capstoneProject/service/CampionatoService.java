package it.epicode.capstoneProject.service;

import it.epicode.capstoneProject.model.entity.Campionato;
import it.epicode.capstoneProject.model.entity.Utente;
import it.epicode.capstoneProject.model.request.CampionatoRequest;
import it.epicode.capstoneProject.repository.CampionatoRepository;
import it.epicode.capstoneProject.security.JwtTools;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CampionatoService {
    private final CampionatoRepository campionatoRepository;
    private final UtenteService utenteService;
    private final JwtTools jwtTools;

    @Transactional
    public Campionato save(CampionatoRequest campionatoRequest, HttpServletRequest request){
        Utente userFromJwt = utenteService.getByUsername(jwtTools.extractUsernameFromAuthorizationHeader(request));
        Campionato campionato = new Campionato();
        campionato.setNome(campionatoRequest.getNome().trim());
        campionato.setCreatore(userFromJwt);
        campionato.setRealDrivers(campionatoRequest.getRealDrivers());
        campionato.setIndependentSprint(campionatoRequest.getIndependentSprint());
        campionato.setSaveQuali(campionatoRequest.getSaveQuali());
        campionato.setPolePoint(campionatoRequest.getPolePoint());
        campionato.setFastestLapPoint(campionatoRequest.getFastestLapPoint());
        campionato.setMinFastestLapPosition(campionatoRequest.getMinFastestLapPosition());
        campionatoRepository.save(campionato);


        return campionato;
    }
}
