package it.epicode.capstoneProject.model.response;

import lombok.Data;

@Data
public class CampionatoOptionsResponse {
    private boolean realDrivers;
    private boolean independentSprint;
    private boolean saveQuali;
    private boolean polePoint;
    private boolean fastestLapPoint;
    private Integer minFastestLapPosition;
}
