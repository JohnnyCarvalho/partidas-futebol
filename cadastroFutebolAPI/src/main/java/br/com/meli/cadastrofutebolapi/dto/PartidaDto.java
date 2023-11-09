package br.com.meli.cadastrofutebolapi.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PartidaDto {

    private String clubeMandante;

    private String clubeVisitante;

    private String estadio;

    private LocalDateTime data;

    private int golsClubeMandante;

    private int golsClubeVisitante;
}
