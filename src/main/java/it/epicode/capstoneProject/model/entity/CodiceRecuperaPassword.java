package it.epicode.capstoneProject.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Data
@Entity
@Table(name = "codici_recupera_password")
public class CodiceRecuperaPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenza_codici_recupera_password")
    @SequenceGenerator(name = "sequenza_codici_recupera_password", allocationSize = 1)
    private int id;
    @ManyToOne
    @JoinColumn(name = "id_utente", unique = true)
    private Utente utente;
    private String codice;
    private LocalDateTime dataGenerazione;
    private boolean accettato;

    public CodiceRecuperaPassword(){}

    public CodiceRecuperaPassword(Utente utente){
        this.utente = utente;
        accettato = false;
    }

    private String generaCodice(){
        StringBuilder codice = new StringBuilder();
        String caratteri = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(caratteri.length());
            codice.append(caratteri.charAt(index));
        }
        return codice.toString();
    }

    public void setCodice(){
        codice = generaCodice();
        dataGenerazione = LocalDateTime.now();
        accettato = false;
    }
}
