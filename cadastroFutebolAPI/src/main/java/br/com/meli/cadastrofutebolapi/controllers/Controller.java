package br.com.meli.cadastrofutebolapi.controllers;

import br.com.meli.cadastrofutebolapi.dto.PartidaDto;
import br.com.meli.cadastrofutebolapi.entities.Partida;
import br.com.meli.cadastrofutebolapi.services.PartidaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/partida")
public class Controller {

    @Autowired
    private PartidaServices partidaServices;

    @PostMapping
    public ResponseEntity<String> postPartida(@RequestBody PartidaDto partidaDto) {
        String response = partidaServices.post(partidaDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{filter}")
    public List<Partida> getPartida(@PathVariable String filter) {
        List<Partida> response = partidaServices.getFiltered(filter);
        return new ResponseEntity<>(response, HttpStatus.OK).getBody();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<String> putPartida(@PathVariable Long id, @RequestBody PartidaDto partidaDto) {
        String response = partidaServices.put(id, partidaDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deletePartida(@PathVariable Long id) {
        String response = partidaServices.delete(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
