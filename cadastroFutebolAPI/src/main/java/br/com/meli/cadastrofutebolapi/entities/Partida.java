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

    @Column
    private String clube_mandante;

    @Column
    private String clube_visitante;

    @Column
    private String estadio;

    @Column
    private String data;

    @Column
    private int gols_clube_mandante;

    @Column
    private int gols_clube_visitante;
}
