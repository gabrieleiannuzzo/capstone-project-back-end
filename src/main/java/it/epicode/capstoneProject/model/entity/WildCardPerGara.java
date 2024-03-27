package it.epicode.capstoneProject.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "wild_cards_per_gara")
public class WildCardPerGara {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenza_wild_cards_per_gara")
    @SequenceGenerator(name = "sequenza_wild_cards_per_gara", initialValue = 1, allocationSize = 1)
    private int id;
    @ManyToOne
    @JoinColumn(name = "id_pilota")
    private Pilota pilota;
    @ManyToOne
    @JoinColumn(name = "id_scuderia")
    private Scuderia scuderia;
    @ManyToOne
    @JoinColumn(name = "id_gara")
    private Gara gara;
}
