package it.epicode.capstoneProject.service;

import it.epicode.capstoneProject.exception.ConflictException;
import it.epicode.capstoneProject.exception.NotFoundException;
import it.epicode.capstoneProject.model.entity.*;
import it.epicode.capstoneProject.model.request.CampionatoRequest;
import it.epicode.capstoneProject.model.request.GaraRequest;
import it.epicode.capstoneProject.model.request.ScuderiaRequest;
import it.epicode.capstoneProject.model.response.CampionatoResponse;
import it.epicode.capstoneProject.model.response.GaraResponse;
import it.epicode.capstoneProject.model.response.PilotaResponse;
import it.epicode.capstoneProject.model.response.ScuderiaResponse;
import it.epicode.capstoneProject.repository.CampionatoRepository;
import it.epicode.capstoneProject.security.JwtTools;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CampionatoService {
    private final CampionatoRepository campionatoRepository;
    private final UtenteService utenteService;
    private final PunteggioService punteggioService;
    private final ScuderiaService scuderiaService;
    private final GaraService garaService;
    private final JwtTools jwtTools;
    private final PilotaService pilotaService;
    private final WildCardPerGaraService wildCardPerGaraService;

    public Campionato getById(int id){
        return campionatoRepository.findById(id).orElseThrow(() -> new NotFoundException("Campionato con id = " + id + " non trovato"));
    }

    public List<CampionatoResponse> getByCreatorUsername(String username){
        List<Campionato> campionati = campionatoRepository.getByCreatorUsername(username);
        List<CampionatoResponse> response = new ArrayList<>();
        for (Campionato c : campionati) response.add(CampionatoResponse.createByCampionato(c));
        return response;
    }

    public CampionatoResponse getCampionatoResponseById(int id) throws NotFoundException{
        CampionatoResponse c = CampionatoResponse.createByCampionato(getById(id));

        List<GaraResponse> gare = c.getGare();

        for (GaraResponse g : gare) {
            g.setSprintQuali(getListByIdsList(g.getSprintQuali(), g.getId()));
            g.setSprintRace(getListByIdsList(g.getSprintRace(), g.getId()));
            g.setSprintRetired(getListByIdsList(g.getSprintRetired(), g.getId()));
            g.setSprintPenalties(getListByIdsList(g.getSprintPenalties(), g.getId()));
            g.setQuali(getListByIdsList(g.getQuali(), g.getId()));
            g.setRace(getListByIdsList(g.getRace(), g.getId()));
            g.setRetired(getListByIdsList(g.getRetired(), g.getId()));
            g.setPenalties(getListByIdsList(g.getPenalties(), g.getId()));
        }

        return c;
    }

    public List<Object> getListByIdsList(List<Object> e, int idGara){
        return e.stream().map(p -> {
            PilotaResponse pilotaResponse = PilotaResponse.createByPilota(pilotaService.getById(Integer.parseInt(p.toString())));
            if (pilotaResponse.isWildCard() || pilotaResponse.isRetired()) pilotaResponse.setScuderia(ScuderiaResponse.createFromScuderia(wildCardPerGaraService.getByIdPilotaAndIdGara(Integer.parseInt(p.toString()), idGara).getScuderia()));
            return (Object) pilotaResponse;
        }).toList();
    }

    public Campionato getByCreatorUsernameAndNome(String username, String nome){
        return campionatoRepository.getByCreatorUsernameAndNome(username, nome).orElseThrow(() -> new NotFoundException("Campionato non trovato"));
    }

    @Transactional
    public CampionatoResponse save(CampionatoRequest campionatoRequest, HttpServletRequest request){
        Utente userFromJwt = utenteService.getByUsername(jwtTools.extractUsernameFromAuthorizationHeader(request));
        List<Campionato> campionatiByUtente = getByCreatorUsername(userFromJwt.getUsername());
        if (campionatiByUtente.size() == 5) throw new ConflictException("Non puoi creare più di 5 campionati");
        for (Campionato c : campionatiByUtente) {
            if (c.getNome().equals(campionatoRequest.getNome().trim())) throw new ConflictException("Hai già creato un campionato con questo nome");
        }
        System.out.println(campionatoRequest);

        Campionato campionato = new Campionato();
        campionato.setNome(campionatoRequest.getNome().trim());
        campionato.setCreator(userFromJwt);
        campionato.setRealDrivers(campionatoRequest.getRealDrivers());
        campionato.setIndependentSprint(campionatoRequest.getIndependentSprint());
        campionato.setSaveQuali(campionatoRequest.getSaveQuali());
        campionato.setPolePoint(campionatoRequest.getPolePoint());
        campionato.setFastestLapPoint(campionatoRequest.getFastestLapPoint());
        campionato.setMinFastestLapPosition(campionatoRequest.getMinFastestLapPosition());
        campionato.setScuderie(new ArrayList<>());
        campionato.setGare(new ArrayList<>());
        campionatoRepository.save(campionato);


        campionato.setPunteggi(punteggioService.save(campionatoRequest.getPunteggi(), campionato));
        for (int i = 0; i < campionatoRequest.getScuderie().size(); i++) {
            ScuderiaRequest scuderiaRequest = campionatoRequest.getScuderie().get(i);
            campionato.getScuderie().add(scuderiaService.save(scuderiaRequest, campionato));
        }
        for (int i = 0; i < campionatoRequest.getGare().size(); i++) {
            GaraRequest garaRequest = campionatoRequest.getGare().get(i);
            campionato.getGare().add(garaService.save(garaRequest, campionato, i + 1));
        }
        campionato.setAdmins(new ArrayList<>());
        campionato.setPiloti(new ArrayList<>());
        return CampionatoResponse.createByCampionato(campionato);
    }
}
