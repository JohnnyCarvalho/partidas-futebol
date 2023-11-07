package br.com.meli.cadastrofutebolapi.repositories;

import br.com.meli.cadastrofutebolapi.entities.Partida;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartidaRepository extends JpaRepository<Partida, Long> {
}
