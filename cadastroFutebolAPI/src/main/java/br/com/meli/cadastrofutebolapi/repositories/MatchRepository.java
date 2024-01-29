package br.com.meli.cadastrofutebolapi.repositories;

import br.com.meli.cadastrofutebolapi.entities.SoccerMatch;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<SoccerMatch, Long> {

    /**
     *
     * @param homeTeam
     * @param visitingTeam
     * @return
     */

    public List<SoccerMatch> findAllByHomeTeamOrVisitingTeamEqualsIgnoreCase(String homeTeam, String visitingTeam);

    public List<SoccerMatch> findAllByHomeTeamEqualsIgnoreCase(String argument);

    public List<SoccerMatch> findAllByVisitingTeamEqualsIgnoreCase(String argument);

    public List<SoccerMatch> findAllByStadiumEqualsIgnoreCase(String argument);

}
