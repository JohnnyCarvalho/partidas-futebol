package br.com.meli.cadastrofutebolapi.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table
public class Clube {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String nome;

    @ManyToOne
    @JoinColumn
    private Estadio estadio;

    @Column
    private String cidade;
}
