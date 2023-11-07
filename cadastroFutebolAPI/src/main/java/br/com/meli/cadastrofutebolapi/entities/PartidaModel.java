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

    @Column(name = "clube_1")
    @ManyToOne
    @JoinColumn(name = "clube_id")
    private ClubeModel clube1;

    @Column(name = "clube_2")
    @ManyToOne
    @JoinColumn(name = "clube_id")
    private ClubeModel clube2;

    @Column(name = "estadio_jogo")
    @OneToOne
    @JoinColumn(name = "id_estadio")
    private EstadioModel estadioJogo;

    @Column(name = "data_jogo")
    private String dataPartida;

    @Column(name = "gols_clube_1")
    private int golsClube1;

    @Column(name = "gols_clube_2")
    private int golsClube2;

}
