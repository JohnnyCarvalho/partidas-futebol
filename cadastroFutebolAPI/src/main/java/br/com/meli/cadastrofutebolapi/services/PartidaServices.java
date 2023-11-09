package br.com.meli.cadastrofutebolapi.services;

import br.com.meli.cadastrofutebolapi.dto.PartidaDto;
import br.com.meli.cadastrofutebolapi.entities.Partida;
import br.com.meli.cadastrofutebolapi.repositories.PartidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PartidaServices {

    @Autowired
    private PartidaRepository partidaRepository;
    Partida partida = new Partida();

    public String post(PartidaDto partidaDto) {

        Partida result = mapDtoToEntity(partidaDto, partida);

        partidaRepository.save(result);
        return "Partida registrada com sucesso!";
    }

    public Partida mapDtoToEntity(PartidaDto partidaDto, Partida partida) {
        partida.setClubeMandante(partidaDto.getClubeMandante());
        partida.setClubeVisitante(partidaDto.getClubeVisitante());
        partida.setData(partidaDto.getData());
        partida.setEstadio(partidaDto.getEstadio());
        partida.setGolsClubeMandante(partidaDto.getGolsClubeMandante());
        partida.setGolsClubeVisitante(partidaDto.getGolsClubeVisitante());

        return partida;
    }

    public List<Partida> get() {
        return partidaRepository.findAll();
    }

    public String put(Long id, PartidaDto partidaDto) {

        Optional<Partida> response = partidaRepository.findById(id);

        if (response.isPresent()) {
            Partida novaPartida = response.get();

            Partida result = mapDtoToEntity(partidaDto, novaPartida);

            partidaRepository.save(result);
            return "Partida atualizada com sucesso!";
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public String delete(Long id) {

        System.out.println("ID: " + id);

        if (partidaRepository.existsById(id)) {
            partidaRepository.deleteById(id);
            return "Partida deletada com sucesso!";
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
