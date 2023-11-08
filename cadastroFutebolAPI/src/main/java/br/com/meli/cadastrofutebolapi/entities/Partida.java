package br.com.meli.cadastrofutebolapi.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @Column(name = "clube_mandante")
    private String clubeMandante;

    @Column(name = "clube_visitante")
    private String clubeVisitante;

    @Column
    private String estadio;

    @Column
    private String data;

    @Column(name = "gols_clube_mandante")
    private int golsClubeMandante;

    @Column(name = "gols_clube_visitante")
    private int golsClubeVisitante;
}
