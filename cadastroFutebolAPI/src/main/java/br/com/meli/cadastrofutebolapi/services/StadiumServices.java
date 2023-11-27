package br.com.meli.cadastrofutebolapi.services;

import br.com.meli.cadastrofutebolapi.entities.SoccerMatch;
import br.com.meli.cadastrofutebolapi.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class StadiumServices {

    @Autowired
    private MatchRepository matchRepository;


    public List<SoccerMatch> getFilteredByStadium(String filter, String stadium) {
        switch (filter) {
            case "buscar-por-estadio":
                return getByStadium(stadium);

            default:
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Filtro de busca não encontrado!");
        }
    }

    private List<SoccerMatch> getByStadium(String stadium) {
        return matchRepository.findAllByStadiumEqualsIgnoreCase(stadium);
    }
}
