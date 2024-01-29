package br.com.meli.cadastrofutebolapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.time.DurationMax;
import org.hibernate.validator.constraints.time.DurationMin;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchDto {

    @NotBlank(message = "O campo Home Team não pode ser nulo!")
    private String homeTeam;

    @NotBlank(message = "O campo Visiting Team não pode ser nulo!")
    private String visitingTeam;

    @NotBlank(message = "O campo Stadium não pode ser nulo!")
    private String stadium;

    //@NotNull
    @PastOrPresent(message = "A data do jogo não pode ser no futuro!")
    private LocalDateTime date;

    @NotNull(message = "Os campos gols deve conter valores >= 0!")
    @PositiveOrZero(message = "O valor do placar deve ser >= 0!")
    private Integer goalsHomeTeam;

    @NotNull(message = "Os campos gols deve conter valores >= 0!")
    @PositiveOrZero(message = "O valor do placar deve ser >= 0!")
    private Integer goalsVisitingTeam;
}

