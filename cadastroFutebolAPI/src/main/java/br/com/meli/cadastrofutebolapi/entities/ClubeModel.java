package br.com.meli.cadastrofutebolapi.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "clubes")
public class ClubeModel {

    private Long id;
    private String nome;
    private String estadio;
    private String cidade;
}
