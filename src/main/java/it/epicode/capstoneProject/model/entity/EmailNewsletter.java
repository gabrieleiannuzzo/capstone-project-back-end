package it.epicode.capstoneProject.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "email_newsletter")
public class EmailNewsletter {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenza_email_newsletter")
    @SequenceGenerator(name = "sequenza_email_newsletter", initialValue = 1, allocationSize = 1)
    private int id;
    @Column(unique = true)
    private String email;
}
