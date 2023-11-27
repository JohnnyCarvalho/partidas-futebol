package br.com.meli.cadastrofutebolapi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class SoccerMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @Column(name = "home_team")
    private String homeTeam;

    @Column(name = "visiting_team")
    private String visitingTeam;

    @Column
    private String stadium;

    @Column
    private LocalDateTime date;

    @Column(name = "goals_home_team")
    private int goalsHomeTeam;

    @Column(name = "goals_visiting_team")
    private int goalsVisitingTeam;

}
