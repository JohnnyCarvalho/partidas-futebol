package br.com.meli.cadastrofutebolapi.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
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
