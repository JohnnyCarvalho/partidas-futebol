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
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

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

    public List<Partida> getFiltered(String filter) {
        switch (filter) {
            case "all":
                return getAllMatch();

            case "goleada":
                return getThrashed();

            case "zerogols":
                return getZeroGoals();

            default:
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public String delete(Long id) {

        if (partidaRepository.existsById(id)) {
            partidaRepository.deleteById(id);
            return "Partida deletada com sucesso!";
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public List<Partida> getAllMatch() {
        return partidaRepository.findAll();
    }

    public List<Partida> getThrashed() {

        return partidaRepository.findAll().stream().filter(list ->
                Math.abs((list.getGolsClubeMandante() - list.getGolsClubeVisitante())) >= 3
                ).collect(Collectors.toList());

    }

    public List<Partida> getZeroGoals() {
        return partidaRepository.findAll().stream().filter(list ->
                Math.abs((list.getGolsClubeMandante() + list.getGolsClubeVisitante())) == 0
        ).collect(Collectors.toList());
    }
}
