package br.com.meli.cadastrofutebolapi.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table
public class Partida {

    //TODO ARRUMAR OS NOMES DOS ATRIBUTOS - OK

    //TODO TIRAR OS NOMES DAS NOTATIONS - OK

    //TODO TROCAR OS NOMES DOS CLUBES E GOLS PARA VISITANTES E MANDANTES - OK

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @ManyToOne
    @JoinColumn
    private Clube clube_mandante;

    @ManyToOne
    @JoinColumn
    private Clube clube_visitante;

    @OneToOne
    @JoinColumn
    private Estadio estadio;

    @Column
    private String data;

    @Column
    private int gols_clube_mandante;

    @Column
    private int gols_clube_visitante;
}
