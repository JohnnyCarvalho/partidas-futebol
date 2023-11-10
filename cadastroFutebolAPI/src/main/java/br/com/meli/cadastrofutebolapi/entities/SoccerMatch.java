package br.com.meli.cadastrofutebolapi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table
public class SoccerMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @NotBlank(message = "O campo Home Team não pode ser nulo!")
    @Column(name = "home_team")
    private String homeTeam;

    @NotBlank(message = "O campo Visiting Team não pode ser nulo!")
    @Column(name = "visiting_team")
    private String visitingTeam;

    @NotBlank(message = "O campo Stadium não pode ser nulo!")
    @Column
    private String stadium;

    @Column
    private LocalDateTime date;

    @Column(name = "goals_home_team")
    private int goalsHomeTeam;

    @Column(name = "goals_visiting_team")
    private int goalsVisitingTeam;
}
