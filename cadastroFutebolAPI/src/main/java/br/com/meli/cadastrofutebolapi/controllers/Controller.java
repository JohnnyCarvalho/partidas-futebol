package br.com.meli.cadastrofutebolapi.controllers;

import br.com.meli.cadastrofutebolapi.dto.MatchDto;
import br.com.meli.cadastrofutebolapi.services.MatchServices;
import br.com.meli.cadastrofutebolapi.services.StadiumServices;
import br.com.meli.cadastrofutebolapi.services.TeamServices;
import br.com.meli.cadastrofutebolapi.entities.SoccerMatch;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Validated
@RestController
@RequestMapping("/partida")
public class Controller {

    @Autowired
    private MatchServices matchServices;

    @Autowired
    private TeamServices teamServices;

    @Autowired
    private StadiumServices stadiumServices;

    @PostMapping
    public ResponseEntity<String> postMatch(@RequestBody @Valid MatchDto matchDto) {
        try{
            return matchServices.post(matchDto);
        } catch (final Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Algo deu errado ao tentar salvar no banco!");
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<String> putMatch(@PathVariable Long id, @RequestBody @Valid MatchDto matchDto) {
        String response = matchServices.put(id, matchDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteMatchById(@PathVariable Long id) {
        String response = matchServices.delete(id);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping(value = "/time/{filter}/{team}")
    public ResponseEntity<List<SoccerMatch>> getByTeam(@PathVariable String filter, @PathVariable(required = false) String team) {
        List<SoccerMatch> response = teamServices.getFilteredByTeam(filter, team);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/estadio/{filter}/{stadium}")
    public ResponseEntity<List<SoccerMatch>> getByStadium(@PathVariable String filter, @PathVariable(required = false) String stadium) {
        List<SoccerMatch> response = stadiumServices.getFilteredByStadium(filter, stadium);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{filter}")
    public ResponseEntity<List<SoccerMatch>> getByMatch(@PathVariable String filter) {
        List<SoccerMatch> response = matchServices.getFilteredByMatch(filter);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> error = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((e) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = ((FieldError) error).getDefaultMessage();

            error.put(fieldName, errorMessage);
        });

        return error;
    }

}
