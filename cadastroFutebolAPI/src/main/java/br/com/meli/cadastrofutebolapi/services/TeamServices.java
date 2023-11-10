package br.com.meli.cadastrofutebolapi.services;

import br.com.meli.cadastrofutebolapi.entities.SoccerMatch;
import br.com.meli.cadastrofutebolapi.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TeamServices {

    @Autowired
    private MatchRepository matchRepository;

    public List<SoccerMatch> getFilteredByTeam(String filter, String team) {
        switch (filter) {
            case "buscar-por-time":
                return getAllByTeam(team);

            case "time-mandante":
                return getHomeTeam(team);

            case "time-visitante":
                return getVisitingTeam(team);

            default:
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private List<SoccerMatch> getAllByTeam(String team) {
        return matchRepository.findAllByHomeTeamOrVisitingTeamEqualsIgnoreCase(team, team);
    }

    private List<SoccerMatch> getHomeTeam(String team) {
        return matchRepository.findAllByHomeTeamEqualsIgnoreCase(team);
    }

    private List<SoccerMatch> getVisitingTeam(String team) {
        return matchRepository.findAllByVisitingTeamEqualsIgnoreCase(team);
    }
}
