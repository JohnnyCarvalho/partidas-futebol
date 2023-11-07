package br.com.meli.cadastrofutebolapi.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "partidas")
public class PartidaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long idPartidas;

    @ManyToOne
    @JoinColumn(name = "id_clube_1")
    private ClubeModel clube1;

    @ManyToOne
    @JoinColumn(name = "id_clube_2")
    private ClubeModel clube2;

    @OneToOne
    @JoinColumn(name = "id_estadio")
    private EstadioModel estadioPartida;

    @Column(name = "data_jogo")
    private String dataPartida;

    @Column(name = "gols_clube_1")
    private int golsClube1;

    @Column(name = "gols_clube_2")
    private int golsClube2;
}
