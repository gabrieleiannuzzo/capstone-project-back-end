package it.epicode.capstoneProject.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "scuderie")
public class Scuderia {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenza_scuderie")
    @SequenceGenerator(name = "sequenza_scuderie", initialValue = 1, allocationSize = 1)
    private int id;
    @ManyToOne
    @JoinColumn(name = "id_campionato")
    private Campionato campionato;
    private String nome;
    private String codiceColore;
    private int punti;
    @OneToMany(mappedBy = "scuderia")
    private List<Pilota> piloti;
}
