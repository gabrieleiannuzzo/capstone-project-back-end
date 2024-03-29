package it.epicode.capstoneProject.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@Table(name = "campionati", uniqueConstraints = {@UniqueConstraint(columnNames = {"nome", "id_creatore"})})
public class Campionato {
    // AGGIUNGERE IL CONTROLLO SU MASSIMO 3 ADMIN
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenza_campionati")
    @SequenceGenerator(name = "sequenza_campionati", initialValue = 1, allocationSize = 1)
    private int id;
    private String nome;
    private boolean realDrivers;
    private boolean independentSprint;
    private boolean saveQuali;
    private boolean polePoint;
    private boolean fastestLapPoint;
    private Integer minFastestLapPosition;
    @ManyToOne
    @JoinColumn(name = "id_creatore")
    private Utente creator;
    @OneToOne(mappedBy = "campionato")
    private Punteggio punteggi;
    @OneToMany(mappedBy = "campionato")
    private List<Scuderia> scuderie;
    @OneToMany(mappedBy = "campionato")
    private List<Pilota> piloti;
    @OneToMany(mappedBy = "campionato")
    private List<Gara> gare;
    @OneToMany(mappedBy = "campionato")
    private List<Admin> admins;
    @OneToMany(mappedBy = "campionato")
    private List<Invito> inviti;
}
