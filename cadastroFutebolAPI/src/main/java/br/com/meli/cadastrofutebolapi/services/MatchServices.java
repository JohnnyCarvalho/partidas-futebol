package br.com.meli.cadastrofutebolapi.services;

import br.com.meli.cadastrofutebolapi.dto.MatchDto;
import br.com.meli.cadastrofutebolapi.entities.SoccerMatch;
import br.com.meli.cadastrofutebolapi.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MatchServices {

    @Autowired
    private MatchRepository matchRepository;

    public String put(Long id, MatchDto matchDto) {

        Optional<SoccerMatch> response = matchRepository.findById(id);

        if (response.isPresent()) {
            verifyRegisterTime(matchDto);

            verifyByStadiumAndDay(matchDto);

            SoccerMatch newMatch = response.get();
            newMatch.setHomeTeam(matchDto.getHomeTeam());
            newMatch.setVisitingTeam(matchDto.getVisitingTeam());
            newMatch.setDate(matchDto.getDate());
            newMatch.setStadium(matchDto.getStadium());
            newMatch.setGoalsHomeTeam(matchDto.getGoalsHomeTeam());
            newMatch.setGoalsVisitingTeam(matchDto.getGoalsVisitingTeam());

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

    public String post(MatchDto matchDto) {

        SoccerMatch match = new SoccerMatch();
        verifyRegisterTime(matchDto);

        verifyByStadiumAndDay(matchDto);

        verifyByDayAndTeam(matchDto, matchDto.getDate());

        match.setHomeTeam(matchDto.getHomeTeam());
        match.setVisitingTeam(matchDto.getVisitingTeam());
        match.setDate(matchDto.getDate());
        match.setStadium(matchDto.getStadium());
        match.setGoalsHomeTeam(matchDto.getGoalsHomeTeam());
        match.setGoalsVisitingTeam(matchDto.getGoalsVisitingTeam());

        matchRepository.save(match);
        return "Partida registrada com sucesso!";
    }

    private void verifyRegisterTime(MatchDto matchDto) {
        if (matchDto.getDate() != null) {
            LocalTime time = matchDto.getDate().toLocalTime();

            if (time.isBefore(LocalTime.of(8, 0)) || time.isAfter(LocalTime.of(22, 0))) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Horário deve ser entre 08:00am e 22:00pm!");
            }
        }
    }

    private void verifyByStadiumAndDay(MatchDto matchDto) {

        List<SoccerMatch> dateFound = matchRepository.findAllByStadiumEqualsIgnoreCase(matchDto.getStadium());

        List<SoccerMatch> dataEncontrada = dateFound.stream().filter((d) ->
             d.getDate().toLocalDate().equals(matchDto.getDate().toLocalDate())
         ).toList();

        if (!dataEncontrada.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe um jogo nesta mesma data!");
        }
    }

    private void verifyByDayAndTeam(MatchDto team, LocalDateTime timeMatch) {

        List<SoccerMatch> homeTeam = matchRepository.findAllByHomeTeamEqualsIgnoreCase(team.getHomeTeam());
        List<SoccerMatch> visitingTeam = matchRepository.findAllByVisitingTeamEqualsIgnoreCase(team.getVisitingTeam());

        List<LocalDateTime> dateHomeMatch = homeTeam.stream()
                .map(SoccerMatch::getDate)
                .toList();

        List<LocalDateTime> dateVisitingMatch = visitingTeam.stream()
                .map(SoccerMatch::getDate)
                .toList();

        List<LocalDateTime> dateMatchAllTeams = new ArrayList<>();
        dateMatchAllTeams.addAll(dateHomeMatch);
        dateMatchAllTeams.addAll(dateVisitingMatch);

        dateMatchAllTeams.forEach((d) -> {
            Duration durationBetweenMatches = Duration.between(d, timeMatch).abs();
            long hoursApart = durationBetweenMatches.toHours();
            if (hoursApart < 48) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é permitido uma partida com intervalo < 48h!");
            }
        });
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
