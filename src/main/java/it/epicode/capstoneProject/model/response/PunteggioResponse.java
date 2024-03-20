package it.epicode.capstoneProject.model.response;

import lombok.Data;

import java.util.List;

@Data
public class PunteggioResponse {
    private List<Integer> sprintPoints;
    private List<Integer> racePoints;
}
