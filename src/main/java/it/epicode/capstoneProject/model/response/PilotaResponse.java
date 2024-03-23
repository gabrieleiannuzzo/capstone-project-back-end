package it.epicode.capstoneProject.model.response;

import it.epicode.capstoneProject.model.entity.Pilota;
import lombok.Data;

@Data
public class PilotaResponse {
    private int id;
    private CampionatoUtenteResponse utente;
    private String nome;
    private boolean wildCard;
    private boolean retired;
    private int punti;
    private ScuderiaResponse scuderia;

    public static PilotaResponse createByPilota(Pilota pilota){
        PilotaResponse response = new PilotaResponse();
        response.setId(pilota.getId());

        if (pilota.getCampionato().isRealDrivers()) {
            CampionatoUtenteResponse c = new CampionatoUtenteResponse();
            c.setId(pilota.getUtente().getId());
            c.setUsername(pilota.getUtente().getUsername());
            c.setUrlFotoProfilo(pilota.getUtente().getUrlFotoProfilo());
            response.setUtente(c);
        } else {
            response.setNome(pilota.getNome());
        }

        response.setWildCard(pilota.getWildCard());
        response.setRetired(pilota.isRetired());
        response.setPunti(pilota.getPunti());
        if (!pilota.getWildCard() && !pilota.isRetired()) response.setScuderia(ScuderiaResponse.createFromScuderia(pilota.getScuderia()));
        return response;
    }
}
