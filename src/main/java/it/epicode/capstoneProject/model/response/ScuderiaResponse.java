package it.epicode.capstoneProject.model.response;

import it.epicode.capstoneProject.model.entity.Scuderia;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ScuderiaResponse {
    private int id;
    private String nome;
    private String codiceColore;
    private int punti;

    public static List<ScuderiaResponse> createFromScuderiaList(List<Scuderia> scuderie){
        List<ScuderiaResponse> scuderieResponse = new ArrayList<>();

        for (Scuderia scuderia : scuderie) {
            ScuderiaResponse response = new ScuderiaResponse();
            response.setId(scuderia.getId());
            response.setNome(scuderia.getNome());
            response.setCodiceColore(scuderia.getCodiceColore());
            response.setPunti(scuderia.getPunti());
            scuderieResponse.add(response);
        }

        return scuderieResponse;
    }
}
