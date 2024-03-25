package it.epicode.capstoneProject.model.response;

import it.epicode.capstoneProject.model.classes.Utility;
import it.epicode.capstoneProject.model.entity.Admin;
import it.epicode.capstoneProject.model.entity.Campionato;
import it.epicode.capstoneProject.model.entity.Pilota;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CampionatoResponse {
    private int id;
    private String nome;
    private CampionatoUtenteResponse creator;
    private CampionatoOptionsResponse options;
    private PunteggioResponse punteggi;
    private List<ScuderiaResponse> scuderie;
    private List<GaraResponse> gare;
    private List<CampionatoUtenteResponse> admins;
    private List<PilotaResponse> pilotiTitolari;
    private List<PilotaResponse> wildCards;
    private List<PilotaResponse> pilotiRitirati;

    public static CampionatoResponse createByCampionato(Campionato campionato){
        CampionatoResponse response = new CampionatoResponse();
        response.setId(campionato.getId());
        response.setNome(campionato.getNome());

        CampionatoUtenteResponse campionatoUtenteResponse = new CampionatoUtenteResponse();
        campionatoUtenteResponse.setId(campionato.getCreator().getId());
        campionatoUtenteResponse.setUsername(campionato.getCreator().getUsername());
        campionatoUtenteResponse.setUrlFotoProfilo(campionato.getCreator().getUrlFotoProfilo());
        response.setCreator(campionatoUtenteResponse);

        CampionatoOptionsResponse options = new CampionatoOptionsResponse();
        options.setRealDrivers(campionato.isRealDrivers());
        options.setIndependentSprint(campionato.isIndependentSprint());
        options.setPolePoint(campionato.isPolePoint());
        options.setSaveQuali(campionato.isSaveQuali());
        options.setFastestLapPoint(campionato.isFastestLapPoint());
        options.setMinFastestLapPosition(campionato.getMinFastestLapPosition());
        response.setOptions(options);

        PunteggioResponse punteggioResponse = new PunteggioResponse();
        punteggioResponse.setSprintPoints(Utility.jsonParseList(campionato.getPunteggi().getSprintPoints()));
        punteggioResponse.setRacePoints(Utility.jsonParseList(campionato.getPunteggi().getRacePoints()));
        response.setPunteggi(punteggioResponse);

        response.setScuderie(ScuderiaResponse.createFromScuderiaList(campionato.getScuderie()));
        response.setGare(GaraResponse.createFromGaraList(campionato.getGare()));

        List<CampionatoUtenteResponse> admins = new ArrayList<>();
        for (Admin a : campionato.getAdmins()) {
            CampionatoUtenteResponse c = new CampionatoUtenteResponse();
            c.setId(a.getId());
            c.setUsername(a.getUtente().getUsername());
            c.setUrlFotoProfilo(a.getUtente().getUrlFotoProfilo());
            admins.add(c);
        }

        response.setAdmins(admins);

        List<PilotaResponse> pilotiTitolari  = new ArrayList<>();
        List<PilotaResponse> wildCards  = new ArrayList<>();
        List<PilotaResponse> pilotiRitirati  = new ArrayList<>();

        for (Pilota p : campionato.getPiloti()) {
            PilotaResponse pr = PilotaResponse.createByPilota(p);
            if (pr.isRetired()) {
                pilotiRitirati.add(pr);
            } else if (pr.isWildCard()) {
                wildCards.add(pr);
            } else {
                pilotiTitolari.add(pr);
            }
        }

        response.setPilotiTitolari(pilotiTitolari);
        response.setWildCards(wildCards);
        response.setPilotiRitirati(pilotiRitirati);

        return response;
    }
}
