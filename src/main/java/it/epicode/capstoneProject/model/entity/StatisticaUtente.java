package it.epicode.capstoneProject.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "statistiche_utenti")
public class StatisticaUtente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenza_statistiche_piloti")
    @SequenceGenerator(name = "sequenza_statistiche_piloti", initialValue = 1, allocationSize = 1)
    private int id;
    @OneToOne
    @JoinColumn(name = "id_utente", unique = true)
    private Utente utente;
    private int numeroGareDisputate;
    private double posizioneMediaGara;
    private double posizioneMediaQualifica;
    private int numeroVittorie;
    private int numeroPolePositions;
    private int posizionamentiTop3;
    private int posizionamentiTop10;
    private int numeroRitiri;
    private int numeroPenalita;

    public StatisticaUtente(){}

    public StatisticaUtente(Utente utente){
        this.utente = utente;
        numeroGareDisputate = 0;
        posizioneMediaGara = 0;
        posizioneMediaQualifica = 0;
        numeroVittorie = 0;
        numeroPolePositions = 0;
        posizionamentiTop3 = 0;
        posizionamentiTop10 = 0;
        numeroRitiri = 0;
        numeroPenalita = 0;
    }
}
