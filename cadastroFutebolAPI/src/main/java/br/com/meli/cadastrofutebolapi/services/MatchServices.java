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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MatchServices {

    @Autowired
    private MatchRepository matchRepository;

    public String put(Long id, MatchDto matchDto) {

        Optional<SoccerMatch> response = matchRepository.findById(id);

        if (response.isPresent()) {
            verifyRegisterTime(matchDto);

            verifyByStadiumAndDay(matchDto);

            verifyByDayAndTeam(matchDto.getHomeTeam(), matchDto.getDate());

            verifyByDayAndTeam(matchDto.getVisitingTeam(), matchDto.getDate());

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
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe nenhuma partida com esse ID!");
    }

    public String delete(Long id) {

        if (matchRepository.existsById(id)) {
            matchRepository.deleteById(id);
            return "Partida deletada com sucesso!";
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Esse id não existe!");
    }

    public String post(MatchDto matchDto) {

        SoccerMatch match = new SoccerMatch();

        verifyRegisterTime(matchDto);

        verifyByStadiumAndDay(matchDto);

        verifyByDayAndTeam(matchDto.getHomeTeam(), matchDto.getDate());

        verifyByDayAndTeam(matchDto.getVisitingTeam(), matchDto.getDate());

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
        if (matchDto.getDate() == null) {
            throw new IllegalArgumentException("A data não pode ser nula");
        }

        LocalTime time = matchDto.getDate().toLocalTime();

        if (time.isBefore(LocalTime.of(8, 0)) || time.isAfter(LocalTime.of(22, 0))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Horário deve ser entre 08:00am e 22:00pm!");
        }
    }

    private void verifyByStadiumAndDay(MatchDto matchDto) {

        List<SoccerMatch> dateFound = matchRepository.findAllByStadiumEqualsIgnoreCase(matchDto.getStadium());

        List<SoccerMatch> dateNotFound = dateFound.stream().filter((d) ->
                d.getDate().toLocalDate().equals(matchDto.getDate().toLocalDate())
        ).toList();

        if (!dateNotFound.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe um jogo nesta mesma data para este estádio!");
        }
    }

    private void verifyByDayAndTeam(String team, LocalDateTime timeMatch) {

        List<SoccerMatch> teams = matchRepository.findAllByHomeTeamOrVisitingTeamEqualsIgnoreCase(team, team);

        List<LocalDateTime> dateMatch = teams.stream()
                .map(SoccerMatch::getDate)
                .toList();

        dateMatch.forEach((d) -> {
            Duration durationBetweenMatches = Duration.between(d, timeMatch).abs();
            long hoursApart = durationBetweenMatches.toHours();
            if (hoursApart < 48) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é permitido uma partida com intervalo < 48h!");
            }
        });
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
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Partida não encontrada!");
        }
    }

    public List<SoccerMatch> getAllMatch() {
        return matchRepository.findAll();
    }

    public List<SoccerMatch> getByThrashed() {

        return matchRepository.findAll().stream().filter(list ->
                Math.abs((list.getGoalsHomeTeam() - list.getGoalsVisitingTeam())) >= 3
        ).collect(Collectors.toList());
    }

    public List<SoccerMatch> getByZeroGoals() {
        return matchRepository.findAll().stream().filter(list ->
                Math.abs((list.getGoalsHomeTeam() + list.getGoalsVisitingTeam())) == 0
        ).collect(Collectors.toList());
    }
}
