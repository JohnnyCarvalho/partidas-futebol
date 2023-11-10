package br.com.meli.cadastrofutebolapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MatchDto {

    private String homeTeam;

    private String visitingTeam;

    private String stadium;

    private LocalDateTime date;

    private int goalsHomeTeam;

    private int golsClubeVisitante;
}
