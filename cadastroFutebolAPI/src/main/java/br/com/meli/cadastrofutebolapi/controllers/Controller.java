package br.com.meli.cadastrofutebolapi.controllers;

import br.com.meli.cadastrofutebolapi.dto.MatchDto;
import br.com.meli.cadastrofutebolapi.entities.SoccerMatch;
import br.com.meli.cadastrofutebolapi.services.MatchServices;
import br.com.meli.cadastrofutebolapi.services.TeamServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/partida")
public class Controller {

    //TODO TENTANDO CRIAR O FILTRO POR TIME

    @Autowired
    private MatchServices matchServices;

    @Autowired
    private TeamServices teamServices;

    @PostMapping
    public ResponseEntity<String> postMatch(@RequestBody MatchDto matchDto) {
        String response = matchServices.post(matchDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{filter}")
    public ResponseEntity<List<SoccerMatch>> getByMatch(@PathVariable String filter) {
        List<SoccerMatch> response = matchServices.getFilteredByMatch(filter);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{filter}/{argument}")
    public List<SoccerMatch> getByTeam(@PathVariable String filter, @PathVariable(required = false) String argument) {
        List<SoccerMatch> response = teamServices.getFilteredByTeamOrStadium(filter, argument);
        return new ResponseEntity<>(response, HttpStatus.OK).getBody();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<String> putMatch(@PathVariable Long id, @RequestBody MatchDto matchDto) {
        String response = matchServices.put(id, matchDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteMatchById(@PathVariable Long id) {
        String response = matchServices.delete(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
