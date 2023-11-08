package br.com.meli.cadastrofutebolapi.dto;

import lombok.Data;

@Data
public class PartidaDto {

    private String clubeMandante;

    private String clubeVisitante;

    private String estadio;

    private String data;

    private int golsClubeMandante;

    private int golsClubeVisitante;
}
