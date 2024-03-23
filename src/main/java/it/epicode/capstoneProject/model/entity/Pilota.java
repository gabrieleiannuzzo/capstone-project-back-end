package it.epicode.capstoneProject.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "piloti")
public class Pilota {
    // AGGIUNGERE CONTROLLO CHE LA WILD CARD NON ABBIA SCUDERIE E CHE I PILOTI TITOLARI CE L'ABBIANO
    // AGGIUNGERE CONTROLLO CHE PILOTI CUSTOM DEVONO AVERE NOMI CUSTOM E PILOTI REALI NON DEVONO AVERLO
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenza_piloti")
    @SequenceGenerator(name = "sequenza_piloti", initialValue = 1, allocationSize = 1)
    private int id;
    @ManyToOne
    @JoinColumn(name = "id_utente")
    private Utente utente;
    private String nome;
    @ManyToOne
    @JoinColumn(name = "id_campionato")
    private Campionato campionato;
    private Boolean wildCard;
    private boolean retired;
    private int punti;
    @ManyToOne
    @JoinColumn(name = "id_scuderia")
    private Scuderia scuderia;
    @OneToMany(mappedBy = "fastestLapDriver")
    private List<Gara> giriVeloci;
}
