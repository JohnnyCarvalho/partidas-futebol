package br.com.meli.cadastrofutebolapi.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "clubes")
public class ClubeModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nome", unique = true)
    private String nomeClube;

    @Column(name = "estadios")
    @ManyToOne
    @JoinColumn(name = "estadio_id")
    private EstadioModel estadioClube;

    @Column(name = "cidade")
    private String cidadeClube;
}
