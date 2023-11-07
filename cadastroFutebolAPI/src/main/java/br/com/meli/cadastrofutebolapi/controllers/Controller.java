package br.com.meli.cadastrofutebolapi.controllers;

import br.com.meli.cadastrofutebolapi.dto.PartidaDto;
import br.com.meli.cadastrofutebolapi.services.PartidaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/partida")
public class Controller {

    @Autowired
    private PartidaServices partidaServices;

    @PostMapping
    public String postPartida(@RequestBody PartidaDto partidaDto) {
        String response = partidaServices.postPartida(partidaDto);
        return response;
    }
}
