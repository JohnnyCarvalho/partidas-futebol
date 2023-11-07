package br.com.meli.cadastrofutebolapi.dto;

import lombok.Data;

@Data
public class PartidaDto {

    //private Long id;

    private String clube_mandante;

    private String clube_visitante;

    private String estadio;

    private String data;

    private int gols_clube_mandante;

    private int gols_clube_visitante;
}
