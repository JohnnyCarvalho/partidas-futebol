package br.com.meli.cadastrofutebolapi.services;

import br.com.meli.cadastrofutebolapi.dto.PartidaDto;
import br.com.meli.cadastrofutebolapi.entities.Partida;
import br.com.meli.cadastrofutebolapi.repositories.PartidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PartidaServices {

    @Autowired
    private PartidaRepository partidaRepository;

    public String postPartida(PartidaDto partidaDto) {

        Partida partida = new Partida();

        partida.setClube_mandante(partidaDto.getClube_mandante());
        partida.setClube_visitante(partidaDto.getClube_visitante());
        partida.setData(partidaDto.getData());
        partida.setEstadio(partidaDto.getEstadio());
        partida.setGols_clube_mandante(partidaDto.getGols_clube_mandante());
        partida.setGols_clube_visitante(partidaDto.getGols_clube_visitante());

        System.out.println("Objeto partida: " + partida);
        partidaRepository.save(partida);

        return "Partida registrada com sucesso!";

    }

    public List<Partida> getPartida() {
        return partidaRepository.findAll();
    }
}
