package br.com.meli.cadastrofutebolapi.services;

import br.com.meli.cadastrofutebolapi.dto.MatchDto;
import br.com.meli.cadastrofutebolapi.entities.SoccerMatch;
import br.com.meli.cadastrofutebolapi.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MatchServices {

    @Autowired
    private MatchRepository matchRepository;

    public String post(MatchDto matchDto) {

        //SoccerMatch result = mapDtoToEntity(matchDto, match);
        SoccerMatch match = new SoccerMatch();

        match.setHomeTeam(matchDto.getHomeTeam());
        match.setVisitingTeam(matchDto.getVisitingTeam());
        match.setDate(matchDto.getDate());
        match.setStadium(matchDto.getStadium());
        match.setGoalsHomeTeam(matchDto.getGoalsHomeTeam());
        match.setGoalsVisitingTeam(matchDto.getGolsClubeVisitante());

        matchRepository.save(match);
        return "Partida registrada com sucesso!";
    }

    public String put(Long id, MatchDto matchDto) {

        Optional<SoccerMatch> response = matchRepository.findById(id);

        if (response.isPresent()) {
            SoccerMatch newMatch = response.get();
            newMatch.setHomeTeam(matchDto.getHomeTeam());
            newMatch.setVisitingTeam(matchDto.getVisitingTeam());
            newMatch.setDate(matchDto.getDate());
            newMatch.setStadium(matchDto.getStadium());
            newMatch.setGoalsHomeTeam(matchDto.getGoalsHomeTeam());
            newMatch.setGoalsVisitingTeam(matchDto.getGolsClubeVisitante());

            matchRepository.save(newMatch);
            return "Partida atualizada com sucesso!";
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public String delete(Long id) {

        if (matchRepository.existsById(id)) {
            matchRepository.deleteById(id);
            return "Partida deletada com sucesso!";
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public List<SoccerMatch> getFilteredByMatch(String filter) {
        switch (filter) {
            case "all":
                return getAllMatch();

            case "goleada":
                return getByThrashed();

            case "zero-gols":
                return getByZeroGoals();

            default:
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private List<SoccerMatch> getAllMatch() {
        return matchRepository.findAll();
    }

    private List<SoccerMatch> getByThrashed() {

        return matchRepository.findAll().stream().filter(list ->
                Math.abs((list.getGoalsHomeTeam() - list.getGoalsVisitingTeam())) >= 3
                ).collect(Collectors.toList());

    }

    private List<SoccerMatch> getByZeroGoals() {
        return matchRepository.findAll().stream().filter(list ->
                Math.abs((list.getGoalsHomeTeam() + list.getGoalsVisitingTeam())) == 0
        ).collect(Collectors.toList());
    }

}