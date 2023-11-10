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

    public List<SoccerMatch> getFilteredByTeamOrStadium(String filter, String argument) {
        switch (filter) {
            case "time":
                return getAllByTeam(argument);

            case "time-mandante":
                return getHomeTeam(argument);

            case "time-visitante":
                return getVisitingTeam(argument);

            default:
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private List<SoccerMatch> getAllByTeam(String argument) {
        return matchRepository.findAllByHomeTeamOrVisitingTeamEqualsIgnoreCase(argument, argument);
    }

    private List<SoccerMatch> getHomeTeam(String argument) {
        return matchRepository.findAllByHomeTeamEqualsIgnoreCase(argument);
    }

    private List<SoccerMatch> getVisitingTeam(String argument) {
        return matchRepository.findAllByVisitingTeamEqualsIgnoreCase(argument);
    }
}
