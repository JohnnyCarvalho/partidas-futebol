package br.com.meli.cadastrofutebolapi.entities;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
@Table(name = "estadios")
public class EstadioModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idEstadio;

    @Column(name = "nome", unique = true)
    private String nomeEstadio;

    @Column(name = "cidade")
    private String cidadeEstadio;

    @Column(name = "capacidade")
    private double capacidadeEstadio;
}
