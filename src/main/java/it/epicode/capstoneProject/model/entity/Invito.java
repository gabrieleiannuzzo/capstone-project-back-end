package it.epicode.capstoneProject.model.entity;

import it.epicode.capstoneProject.model.enums.RuoloInvito;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "inviti")
public class Invito {
    // AGGIUNGERE CONTROLLO CHE NON SI INVITI SE STESSI
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenza_inviti")
    @SequenceGenerator(name = "sequenza_inviti", initialValue = 1, allocationSize = 1)
    private int id;
    @ManyToOne
    @JoinColumn(name = "id_campionato")
    private Campionato campionato;
    @ManyToOne
    private Utente fromUser;
    @ManyToOne
    private Utente toUser;
    private Boolean accepted;
    @Enumerated(EnumType.STRING)
    private RuoloInvito ruoloInvito;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
