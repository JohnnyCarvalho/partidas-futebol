package br.com.meli.cadastrofutebolapi.repositories;

import br.com.meli.cadastrofutebolapi.entities.SoccerMatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchRepository extends JpaRepository<SoccerMatch, Long> {

    public List<SoccerMatch> findAllByHomeTeamOrVisitingTeamEqualsIgnoreCase(String homeTeam, String visitingTeam);

    public List<SoccerMatch> findAllByHomeTeamEqualsIgnoreCase(String argument);

    public List<SoccerMatch> findAllByVisitingTeamEqualsIgnoreCase(String argument);

    public List<SoccerMatch> findAllByStadiumEqualsIgnoreCase(String argument);

}
