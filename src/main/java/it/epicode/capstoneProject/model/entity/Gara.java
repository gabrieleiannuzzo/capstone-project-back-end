package it.epicode.capstoneProject.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "gare")
public class Gara {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenza_gare")
    @SequenceGenerator(name = "sequenza_gare", initialValue = 1, allocationSize = 1)
    private int id;
    @ManyToOne
    @JoinColumn(name = "id_campionato")
    @ToString.Exclude
    private Campionato campionato;
    private String nome;
    private int numeroGara;
    private boolean sprint;
    private String sprintQuali;
    private String sprintRace;
    private String sprintRetired;
    private String sprintPenalties;
    private String quali;
    private String race;
    private String retired;
    private String penalties;
    @ManyToOne
    @JoinColumn(name = "id_pilota_giro_veloce")
    private Pilota fastestLapDriver;
}