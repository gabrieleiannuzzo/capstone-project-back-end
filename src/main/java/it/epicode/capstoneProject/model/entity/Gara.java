package it.epicode.capstoneProject.model.entity;

import it.epicode.capstoneProject.model.classes.Utility;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

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
    @OneToMany(mappedBy = "gara")
    private List<WildCardPerGara> wildCards;

    public Gara(){
        sprintQuali = Utility.jsonStringify(new ArrayList<Integer>());
        sprintRace = Utility.jsonStringify(new ArrayList<Integer>());
        sprintRetired = Utility.jsonStringify(new ArrayList<Integer>());
        sprintPenalties = Utility.jsonStringify(new ArrayList<Integer>());
        quali = Utility.jsonStringify(new ArrayList<Integer>());
        race = Utility.jsonStringify(new ArrayList<Integer>());
        retired = Utility.jsonStringify(new ArrayList<Integer>());
        penalties = Utility.jsonStringify(new ArrayList<Integer>());
    }
}