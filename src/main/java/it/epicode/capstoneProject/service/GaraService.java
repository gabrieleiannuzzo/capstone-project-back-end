package it.epicode.capstoneProject.service;

import it.epicode.capstoneProject.exception.ConflictException;
import it.epicode.capstoneProject.exception.NotFoundException;
import it.epicode.capstoneProject.model.classes.Utility;
import it.epicode.capstoneProject.model.entity.Campionato;
import it.epicode.capstoneProject.model.entity.Gara;
import it.epicode.capstoneProject.model.entity.Pilota;
import it.epicode.capstoneProject.model.entity.Utente;
import it.epicode.capstoneProject.model.request.AggiornaGaraRequest;
import it.epicode.capstoneProject.model.request.GaraRequest;
import it.epicode.capstoneProject.model.request.WildCardPerGaraRequest;
import it.epicode.capstoneProject.repository.GaraRepository;
import it.epicode.capstoneProject.security.JwtTools;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GaraService {
    private final GaraRepository garaRepository;
    private final UtenteService utenteService;
    private final PilotaService pilotaService;
    private final JwtTools jwtTools;
    private final ScuderiaService scuderiaService;
    private final WildCardPerGaraService wildCardPerGaraService;

    public Gara getById(int id){
        return garaRepository.findById(id).orElseThrow(() -> new NotFoundException("Gara con id = " + id + " non trovata"));
    }

    public Gara save(GaraRequest garaRequest, Campionato campionato, int numeroGara){
        Gara gara = new Gara();
        gara.setCampionato(campionato);
        gara.setNome(garaRequest.getNome().trim());
        gara.setSprint(garaRequest.getSprint());
        gara.setNumeroGara(numeroGara);
        return garaRepository.save(gara);
    }

    public void aggiornaGara(AggiornaGaraRequest aggiornaGaraRequest, HttpServletRequest request){
        Gara g = getById(aggiornaGaraRequest.getIdGara());
        Campionato c = g.getCampionato();
        Utente u = utenteService.getByUsername(jwtTools.extractUsernameFromAuthorizationHeader(request));

        Utility.isUserAuthorizedToChampionship(c, u, "Non sei autorizzato a gestire questo campionato");

        // controllo che i risultati della gara non siano già stati inseriti
        if (!isListEmpty(g.getSprintQuali()) || !isListEmpty(g.getSprintRace()) || !isListEmpty(g.getSprintRetired()) || !isListEmpty(g.getSprintPenalties()) || !isListEmpty(g.getQuali()) || !isListEmpty(g.getRace()) || !isListEmpty(g.getRetired()) || !isListEmpty(g.getPenalties()) || g.getFastestLapDriver() != null || !g.getWildCards().isEmpty()) throw new ConflictException("Risultati gara già inseriti");
        // controllo giro veloce
        if (c.isFastestLapPoint()) {
            if (aggiornaGaraRequest.getIdPilotaFastestLap() == null) throw new ConflictException("Devi inserire il giro veloce");
            Pilota p = pilotaService.getById(aggiornaGaraRequest.getIdPilotaFastestLap());
            if (!c.getPiloti().stream().anyMatch(pilota -> pilota.getId() == p.getId())) throw new ConflictException("Il pilota deve appartenere al campionato");
            if (p.isRetired()) throw new ConflictException("Il pilota non può essere ritirato");
        }
        // controllo qualifiche
        if (c.isSaveQuali() && !isListValid(aggiornaGaraRequest.getQuali())) throw new ConflictException("Devi inserire le qualifiche");
        if (c.isPolePoint() && !isListValid(aggiornaGaraRequest.getQuali())) {
            if (c.isSaveQuali()) throw new ConflictException("Devi inserire i risultati delle qualifiche");
            if (c.isPolePoint()) throw new ConflictException("Devi inserire la pole position");
        }
        // controlli sprint
        if (!g.isSprint() && (!aggiornaGaraRequest.getSprintQuali().isEmpty() || !aggiornaGaraRequest.getSprintRace().isEmpty() || !aggiornaGaraRequest.getSprintPenalties().isEmpty() || !aggiornaGaraRequest.getSprintRetired().isEmpty())) throw new ConflictException("Non possono esserci dati sprint");
        if (g.isSprint()) {
            if (!isListValid(aggiornaGaraRequest.getSprintRace())) throw new ConflictException("Devi inserire la sprint race");
            if (c.isSaveQuali() && c.isIndependentSprint() && !isListValid(aggiornaGaraRequest.getSprintQuali())) throw new ConflictException("Devi inserire le qualifiche sprint");
        }
        // controlli piloti
        List<List<Integer>> eventi = List.of(aggiornaGaraRequest.getSprintQuali(), aggiornaGaraRequest.getSprintRace(), aggiornaGaraRequest.getSprintPenalties(), aggiornaGaraRequest.getSprintRetired(), aggiornaGaraRequest.getQuali(), aggiornaGaraRequest.getRace(), aggiornaGaraRequest.getPenalties(), aggiornaGaraRequest.getRetired());
        for (WildCardPerGaraRequest w : aggiornaGaraRequest.getWildCards()) {
            int id = w.getIdWildCard();
            if (!listsContainWildCard(eventi, id)) throw new ConflictException("Alcune wild card non sono utilizzate");
            if (!isScuderiaInChampionship(w.getIdScuderia(), c)) throw new ConflictException("Le scuderie devono appartenere al campionato");
        }
        if (listsContainRetiredDrivers(eventi, c)) throw new ConflictException("I piloti ritirati non possono partecipare");
        if (!areDriversInChampionship(eventi, c)) throw new ConflictException("I piloti devono appartenere al campionato");
        if (!areDriversInRace(aggiornaGaraRequest.getRace(), aggiornaGaraRequest.getRetired())) throw new ConflictException("I piloti ritirati devono aver corso");
        if (!areDriversInRace(aggiornaGaraRequest.getRace(), aggiornaGaraRequest.getPenalties())) throw new ConflictException("I piloti penalizzati devono aver corso");
        if (g.isSprint()) {
            if (!areDriversInRace(aggiornaGaraRequest.getSprintRace(), aggiornaGaraRequest.getSprintRetired())) throw new ConflictException("I piloti ritirati devono aver corso");
            if (!areDriversInRace(aggiornaGaraRequest.getSprintRace(), aggiornaGaraRequest.getSprintPenalties())) throw new ConflictException("I piloti penalizzati devono aver corso");
        }
        if (c.isFastestLapPoint() && !aggiornaGaraRequest.getRace().contains(aggiornaGaraRequest.getIdPilotaFastestLap())) throw new ConflictException("Il giro veloce può essere fatto solo da chi ha corso la gara");

        if (g.isSprint()) {
            if (c.isSaveQuali() && c.isIndependentSprint()) g.setSprintQuali(Utility.jsonStringify(aggiornaGaraRequest.getSprintQuali()));
            g.setSprintRace(Utility.jsonStringify(aggiornaGaraRequest.getSprintRace()));
            g.setSprintRetired(Utility.jsonStringify(aggiornaGaraRequest.getSprintRetired()));
            g.setSprintPenalties(Utility.jsonStringify(aggiornaGaraRequest.getSprintPenalties()));
        }
        if (c.isSaveQuali()) g.setQuali(Utility.jsonStringify(aggiornaGaraRequest.getQuali()));
        g.setRace(Utility.jsonStringify(aggiornaGaraRequest.getRace()));
        g.setRetired(Utility.jsonStringify(aggiornaGaraRequest.getRetired()));
        g.setPenalties(Utility.jsonStringify(aggiornaGaraRequest.getPenalties()));
        if (c.isFastestLapPoint()) g.setFastestLapDriver(pilotaService.getById(aggiornaGaraRequest.getIdPilotaFastestLap()));

        // INSERIMENTO PILOTI IN SCUDERIE_PER_GARA
        List<Object> idPilotiList = new ArrayList<>();
        for (Object o : aggiornaGaraRequest.getRace()) {
            if (!idPilotiList.contains(o)) idPilotiList.add(o);
        }
        if (c.isSaveQuali() || c.isPolePoint()) {
            for (Object o : aggiornaGaraRequest.getQuali()) {
                if (!idPilotiList.contains(o)) idPilotiList.add(o);
            }
        }
        if (g.isSprint()) {
            for (Object o : aggiornaGaraRequest.getSprintRace()) {
                if (!idPilotiList.contains(o)) idPilotiList.add(o);
            }
            if (c.isSaveQuali() && c.isIndependentSprint()) {
                for (Object o : aggiornaGaraRequest.getSprintQuali()) {
                    if (!idPilotiList.contains(o)) idPilotiList.add(o);
                }
            }
        }

        for (Object o : idPilotiList) {
            int idPilota = (Integer) o;
            Pilota p = pilotaService.getById(idPilota);
            if (p.isWildCard()) {
                int idScuderia = aggiornaGaraRequest.getWildCards().stream().filter(w -> w.getIdWildCard() == idPilota).toList().get(0).getIdScuderia();
                wildCardPerGaraService.save(p, scuderiaService.getById(idScuderia), g);
            } else {
                wildCardPerGaraService.save(p, p.getScuderia(), g);
            }
        }

        for (int idPilota : aggiornaGaraRequest.getRace()) {
            Pilota pilota = pilotaService.getById(idPilota);
            pilotaService.updateStatistiche(aggiornaGaraRequest, pilota);
        }

        if (g.isSprint()) {
            for (int idPilota : aggiornaGaraRequest.getSprintRace()) {
                Pilota pilota = pilotaService.getById(idPilota);
                pilotaService.updateStatisticheSprint(aggiornaGaraRequest, pilota);
            }
        }

        if (g.getCampionato().isPolePoint()) {
            Pilota pilota = pilotaService.getById(aggiornaGaraRequest.getQuali().getFirst());
            pilotaService.updatePunti(pilota, 1);
            scuderiaService.updatePunti(pilotaService.getScuderiaFromPilotaAndGara(pilota, g), 1);
        }

        if (g.getCampionato().isFastestLapPoint()) {
            Pilota pilota = pilotaService.getById(aggiornaGaraRequest.getIdPilotaFastestLap());
            for (int i = 0; i < g.getCampionato().getMinFastestLapPosition(); i++) {
                if (aggiornaGaraRequest.getRace().size() >= i + 1) {
                    if (aggiornaGaraRequest.getRace().get(i) == pilota.getId()) {
                        pilotaService.updatePunti(pilota, 1);
                        scuderiaService.updatePunti(pilotaService.getScuderiaFromPilotaAndGara(pilota, g), 1);
                    }
                }
            }
        }

        List<Integer> racePoints = Utility.jsonParseList(g.getCampionato().getPunteggi().getRacePoints());
        for (int i = 0; i < aggiornaGaraRequest.getRace().size(); i++) {
            Pilota pilota = pilotaService.getById(aggiornaGaraRequest.getRace().get(i));
            pilotaService.updatePunti(pilota, racePoints.get(i));
            scuderiaService.updatePunti(pilotaService.getScuderiaFromPilotaAndGara(pilota, g), racePoints.get(i));
        }

        if (g.isSprint()) {
            List<Integer> sprintPoints = Utility.jsonParseList(g.getCampionato().getPunteggi().getSprintPoints());
            for (int i = 0; i < aggiornaGaraRequest.getSprintRace().size(); i++) {
                Pilota pilota = pilotaService.getById(aggiornaGaraRequest.getSprintRace().get(i));
                pilotaService.updatePunti(pilota, sprintPoints.get(i));
                scuderiaService.updatePunti(pilotaService.getScuderiaFromPilotaAndGara(pilota, g), sprintPoints.get(i));
            }
        }
    }

    public boolean isListValid(List<Integer> event){
        return (event != null && !event.isEmpty());
    }

    public boolean isListEmpty(String list){
        return Utility.jsonParseList(list).isEmpty();
    }

    public boolean listsContainWildCard(List<List<Integer>> eventi, int idWildCard){
        for (List<Integer> e : eventi) {
            if (e == null) continue;
            if (e.isEmpty()) continue;
            if (!e.contains(idWildCard)) continue;
            return true;
        }
        return false;
    }

    public boolean listsContainRetiredDrivers(List<List<Integer>> eventi, Campionato c){
        List<Pilota> pilotiRitirati = c.getPiloti().stream().filter(Pilota::isRetired).toList();
        for (Pilota p : pilotiRitirati) {
            for (List<Integer> e : eventi) {
                if (e == null) continue;
                if (e.isEmpty()) continue;
                for (Integer pilota : e) {
                    if (p.getId() == pilota) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean areDriversInChampionship(List<List<Integer>> eventi, Campionato c){
        for (List<Integer> e : eventi) {
            if (e == null) continue;
            if (e.isEmpty()) continue;
            for (Integer p : e) {
                if (!c.getPiloti().stream().anyMatch(pilota -> pilota.getId() == p)) return false;
            }
        }
        return true;
    }

    public boolean isScuderiaInChampionship(int idScuderia, Campionato c){
        return c.getScuderie().stream().anyMatch(s -> s.getId() == idScuderia);
    }

    public boolean areDriversInRace(List<Integer> evento, List<Integer> lista){
        if (lista == null || lista.isEmpty()) return true;
        for (Integer p : lista) {
            if (!evento.contains(p)) return false;
        }
        return true;
    }

    public void deleteById(int id){
        Gara g = getById(id);
        garaRepository.delete(g);
    }

    public void deleteAll(){
        garaRepository.deleteAll();
    }
}
