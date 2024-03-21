package it.epicode.capstoneProject.model.response;

import it.epicode.capstoneProject.model.classes.Utility;
import it.epicode.capstoneProject.model.entity.Campionato;
import lombok.Data;

import java.util.List;

@Data
public class CampionatoResponse {
    private int id;
    private String nome;
    private int idCreator;
    private CampionatoOptionsResponse options;
    private PunteggioResponse punteggi;
    private List<ScuderiaResponse> scuderie;
    private List<GaraResponse> gare;

    public static CampionatoResponse createByCampionato(Campionato campionato){
        CampionatoResponse response = new CampionatoResponse();
        response.setId(campionato.getId());
        response.setNome(campionato.getNome());
        response.setIdCreator(campionato.getCreator().getId());

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

        return response;
    }
}
