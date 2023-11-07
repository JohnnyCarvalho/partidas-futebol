package br.com.meli.cadastrofutebolapi.entities;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
@Table(name = "estadios")
public class EstadioModel {

    @Id
    private Long idEstadio;
    private String nome;
    private String cidade;
    private double capacidade;
}
