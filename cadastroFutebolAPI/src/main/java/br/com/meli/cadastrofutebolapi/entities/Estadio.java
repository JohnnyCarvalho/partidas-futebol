package br.com.meli.cadastrofutebolapi.entities;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
@Table
public class Estadio {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String nome;

    @Column
    private String cidade;

    @Column
    private double capacidade;
}
