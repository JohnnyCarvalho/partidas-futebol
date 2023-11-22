package br.com.meli.cadastrofutebolapi.services;

import br.com.meli.cadastrofutebolapi.dto.MatchDto;
import br.com.meli.cadastrofutebolapi.entities.SoccerMatch;
import br.com.meli.cadastrofutebolapi.repositories.MatchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MatchServicesTest {

    @Mock
    private MatchRepository matchRepository;

    @InjectMocks
    private MatchServices matchServices;

    private static final Long idExisting = 1L;

    private static final Long idNonExisting = 10235L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    // TESTS FOR PUT METHOD
    @Test
    void updateMatchSuccess() {
        MatchDto matchDto = new MatchDto("TeamC", "TeamD", "StadiumX", LocalDateTime.now(), 3, 2);

        when(matchRepository.findById(idExisting)).thenReturn(Optional.of(new SoccerMatch()));
        when(matchRepository.findAllByStadiumEqualsIgnoreCase("StadiumX")).thenReturn(Collections.emptyList());

        String response = matchServices.put(idExisting, matchDto);

        assertEquals("Partida atualizada com sucesso!", response);
        verify(matchRepository).findById(idExisting);
        verify(matchRepository).save(any());
        verify(matchRepository).findAllByStadiumEqualsIgnoreCase("StadiumX");
    }

    @Test
    void searchByIdToUpdateMatchUnknownIdException() {
        MatchDto matchDto = new MatchDto("TeamC", "TeamD", "StadiumX", LocalDateTime.now(), 3, 2);

        assertThrows(ResponseStatusException.class, () -> matchServices.put(idNonExisting, matchDto));

        verify(matchRepository).findById(idNonExisting);
        verify(matchRepository, never()).save(any());
    }


    // TESTS FOR DELETE METHOD
    @Test
    void deleteByIdMatchSuccess() {
        when(matchRepository.existsById(idExisting)).thenReturn(true);
        doNothing().when(matchRepository).deleteById(idExisting);

        String response = matchServices.delete(idExisting);

        assertEquals("Partida deletada com sucesso!", response);
        verify(matchRepository).existsById(idExisting);
        verify(matchRepository).deleteById(idExisting);
        verifyNoMoreInteractions(matchRepository);
    }

    @Test
    void searchByIdToDeleteMatchUnknownIdException() {
        MatchDto matchDto = new MatchDto("TeamC", "TeamD", "StadiumX", LocalDateTime.now(), 3, 2);

        assertThrows(ResponseStatusException.class, () -> matchServices.put(idNonExisting, matchDto));

        verify(matchRepository).findById(idNonExisting);
        verify(matchRepository, never()).delete(any());
    }


//    TESTS FOR POST METHOD
    @Test
    void postMatchTestSuccess() {
        MatchDto matchDto = new MatchDto("Team1", "Team2", "StadiumX", LocalDateTime.now(), 3, 2);

        when(matchRepository.findAllByStadiumEqualsIgnoreCase("StadiumX")).thenReturn(Collections.emptyList());
        when(matchRepository.findAllByHomeTeamOrVisitingTeamEqualsIgnoreCase(anyString(), anyString())).thenReturn(Collections.emptyList());

        String response = matchServices.post(matchDto);

        verify(matchRepository).findAllByStadiumEqualsIgnoreCase("StadiumX");
        verify(matchRepository, times(2)).findAllByHomeTeamOrVisitingTeamEqualsIgnoreCase(anyString(), anyString());
        verify(matchRepository).save(any());

        assertEquals("Partida registrada com sucesso!", response);
    }

    @Test
    void postMatchTestError() {
        MatchDto matchDtoTimeMin = new MatchDto("Team1", "Team2", "StadiumX", LocalDateTime.of(2023, 11, 20, 7, 59), 3, 2);
        MatchDto matchDtoTimeMax = new MatchDto("Team1", "Team2", "StadiumX", LocalDateTime.of(2023, 11, 20, 22, 1), 3, 2);
        MatchDto matchDtoVerifyByStadiumAndDay = new MatchDto("Team1", "Team2", "StadiumX", LocalDateTime.now(), 3, 2);
        MatchDto matchDtoVerifyByDayAndTeam = new MatchDto("Team1", "Team2", "StadiumX", LocalDateTime.now(), 3, 2);

        MatchServices matchServicesMock = mock(MatchServices.class);

        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Horário deve ser entre 08:00am e 22:00pm!")).when(matchServicesMock).post(matchDtoTimeMin);
        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Horário deve ser entre 08:00am e 22:00pm!")).when(matchServicesMock).post(matchDtoTimeMax);
        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe um jogo nesta mesma data para este estádio!")).when(matchServicesMock).post(matchDtoVerifyByStadiumAndDay);
        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é permitido uma partida com intervalo < 48h!")).when(matchServicesMock).post(matchDtoVerifyByDayAndTeam);


    }

    @Test
    void verifyRegisterTimeTest() {

    }

    @Test
    void verifyRegisterTimeTestUnknownIdException() {

    }

    @Test
    void verifyByStadiumAndDayTest() {

    }

    @Test
    void verifyByDayAndTeamTest() {

    }
}