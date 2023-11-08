package br.com.meli.cadastrofutebolapi.controllers;

import br.com.meli.cadastrofutebolapi.dto.PartidaDto;
import br.com.meli.cadastrofutebolapi.entities.Partida;
import br.com.meli.cadastrofutebolapi.services.PartidaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/partida")
public class Controller {

    @Autowired
    private PartidaServices partidaServices;

    @PostMapping
    public String postPartida(@RequestBody PartidaDto partidaDto) {
        String response = partidaServices.post(partidaDto);
        return response;
    }

    @GetMapping
    public List<Partida> getPartida() {
        List<Partida> response = partidaServices.get();
        return response;
    }

    @PutMapping(value = "/{id}")
    public String putPartida(@PathVariable Long id, @RequestBody PartidaDto partidaDto) {
        String response = partidaServices.put(id, partidaDto);
        return response;
    }

    @DeleteMapping(value = "/{id}")
    public String deletePartida(@PathVariable Long id) {
        String response = partidaServices.delete(id);
        return response;
    }

}
