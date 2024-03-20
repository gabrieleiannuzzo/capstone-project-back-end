package it.epicode.capstoneProject.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "punteggi")
public class Punteggio {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenza_punteggi")
    @SequenceGenerator(name = "sequenza_punteggi", initialValue = 1, allocationSize = 1)
    private int id;
    @OneToOne(mappedBy = "punteggi")
    @JoinColumn(name = "id_campionato")
    private Campionato campionato;
    private String sprintPoints;
    private String racePoints;
}
