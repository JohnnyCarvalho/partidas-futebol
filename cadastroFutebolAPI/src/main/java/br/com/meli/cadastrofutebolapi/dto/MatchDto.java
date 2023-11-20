package br.com.meli.cadastrofutebolapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.hibernate.validator.constraints.time.DurationMax;
import org.hibernate.validator.constraints.time.DurationMin;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
public class MatchDto {

    @NotBlank(message = "O campo Home Team não pode ser nulo!")
    private String homeTeam;

    @NotBlank(message = "O campo Visiting Team não pode ser nulo!")
    private String visitingTeam;

    @NotBlank(message = "O campo Stadium não pode ser nulo!")
    private String stadium;

    @NotNull
    @PastOrPresent(message = "A data do jogo não pode ser no futuro!")
    private LocalDateTime date;

    @NotNull
    @PositiveOrZero(message = "O valor do placar deve ser >= 0!")
    private Integer goalsHomeTeam;

    @NotNull
    @PositiveOrZero(message = "O valor do placar deve ser >= 0!")
    private Integer goalsVisitingTeam;

    public MatchDto(String homeTeam, String visitingTeam, String stadium, LocalDateTime date, Integer goalsHomeTeam, Integer goalsVisitingTeam) {
        this.homeTeam = homeTeam;
        this.visitingTeam = visitingTeam;
        this.stadium = stadium;
        this.date = date;
        this.goalsHomeTeam = goalsHomeTeam;
        this.goalsVisitingTeam = goalsVisitingTeam;
    }

    public MatchDto() {
    }

    public MatchDto(String teamA, String teamB, String stadiumX, String string, Integer goalsHomeTeam, Integer goalsVisitingTeam) {
    }
}
