package br.com.meli.cadastrofutebolapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MatchDto {

    @NotBlank(message = "O campo Home Team não pode ser nulo!")
    private String homeTeam;

    @NotBlank(message = "O campo Visiting Team não pode ser nulo!")
    private String visitingTeam;

    @NotBlank(message = "O campo Stadium não pode ser nulo!")
    private String stadium;

    @NotNull(message = "O campo Data não pode ser nulo!")
    private LocalDateTime date;

    private int goalsHomeTeam;

    private int golsClubeVisitante;
}
