package it.epicode.capstoneProject.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "statistiche_sprint_utenti")
public class StatisticaSprintUtente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenza_statistiche_sprint_utenti")
    @SequenceGenerator(name = "sequenza_statistiche_sprint_utenti", initialValue = 1, allocationSize = 1)
    private int id;
    @OneToOne
    @JoinColumn(name = "id_utente", unique = true)
    private Utente utente;
    private int numeroSprintDisputate;
    private double posizioneMediaGara;
    private int numeroVittorie;
    private int posizionamentiTop3;
    private int numeroRitiri;
    private int numeroPenalita;

    public StatisticaSprintUtente(){}

    public StatisticaSprintUtente(Utente utente){
        this.utente = utente;
        numeroSprintDisputate = 0;
        posizioneMediaGara = 0;
        numeroVittorie = 0;
        posizionamentiTop3 = 0;
        numeroRitiri = 0;
        numeroPenalita = 0;
    }
}
