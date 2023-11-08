package br.com.meli.cadastrofutebolapi.services;

import br.com.meli.cadastrofutebolapi.dto.PartidaDto;
import br.com.meli.cadastrofutebolapi.entities.Partida;
import br.com.meli.cadastrofutebolapi.repositories.PartidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PartidaServices {

    @Autowired
    private PartidaRepository partidaRepository;
    public String post(PartidaDto partidaDto) {

        Partida partida = new Partida();
        Object result = objetoModelo(partida, partidaDto);

        if (result != null) {
            return "Partida registrada com sucesso!";
        }
        return "Erro ao registrar a partida!";
    }
    public Object objetoModelo(Partida partida, PartidaDto partidaDto) {
        partida.setClubeMandante(partidaDto.getClubeMandante());
        partida.setClubeVisitante(partidaDto.getClubeVisitante());
        partida.setData(partidaDto.getData());
        partida.setEstadio(partidaDto.getEstadio());
        partida.setGolsClubeMandante(partidaDto.getGolsClubeMandante());
        partida.setGolsClubeVisitante(partidaDto.getGolsClubeVisitante());

        return partidaRepository.save(partida);
    }
    public List<Partida> get() {
        return partidaRepository.findAll();
    }
    public String put(Long id, PartidaDto partidaDto) {

        Optional<Partida> response = partidaRepository.findById(id);

        if (response.isPresent()) {
            Partida novaPartida = response.get();

            objetoModelo(novaPartida, partidaDto);

            partidaRepository.save(novaPartida);
            return "Partida atualizada com sucesso!";
        }
        return "";
    }
    public String delete(Long id) {

        System.out.println("ID: " + id);

        if (partidaRepository.existsById(id)) {
            partidaRepository.deleteById(id);
            return "Partida deletada com sucesso!";
        }
        return "Partida não existe!";
    }
}
