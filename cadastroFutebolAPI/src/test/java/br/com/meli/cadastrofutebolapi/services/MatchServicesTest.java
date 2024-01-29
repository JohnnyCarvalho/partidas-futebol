package br.com.meli.cadastrofutebolapi.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import br.com.meli.cadastrofutebolapi.dto.MatchDto;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

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
    void putMatchTestSuccess() {
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
    void putMatchTestErrorUnknownId() {
        MatchDto matchDto = new MatchDto("TeamC", "TeamD", "StadiumX", LocalDateTime.now(), 3, 2);
        when(matchRepository.findAllByStadiumEqualsIgnoreCase("StadiumX")).thenReturn(Collections.emptyList());

        assertThrows(ResponseStatusException.class, () -> matchServices.put(idNonExisting, matchDto));
        verify(matchRepository).findById(idNonExisting);
        verify(matchRepository, never()).save(any());
    }

    @Test
    void putVerifyRegisterTimeTestDateNull() {

        MatchDto matchDto = new MatchDto("Team1", "Team2", "StadiumX", null, 3, 2);
        when(matchRepository.findById(idExisting)).thenReturn(Optional.of(new SoccerMatch()));

        IllegalArgumentException exception =  assertThrows(IllegalArgumentException.class, () -> matchServices.put(idExisting, matchDto));

        assertEquals("A data não pode ser nula", exception.getMessage());

    }

    @Test
    void putVerifyByStadiumAndDayTestSuccess() {
        MatchDto matchDto = new MatchDto("Team1", "Team2", "StadiumX", LocalDateTime.now(), 3, 2);

        List<SoccerMatch> soccerMatches = Arrays.asList(new SoccerMatch(idExisting, "TeamA", "TeamB", "StadiumX", LocalDateTime.now(), 1, 0));

        when(matchRepository.findAllByStadiumEqualsIgnoreCase(matchDto.getStadium())).thenReturn(soccerMatches);
        when(matchRepository.findById(idExisting)).thenReturn(Optional.of(new SoccerMatch()));

        List<SoccerMatch> response = matchRepository.findAllByStadiumEqualsIgnoreCase(matchDto.getStadium());

        assertEquals(soccerMatches, response);

        verify(matchRepository).findAllByStadiumEqualsIgnoreCase(matchDto.getStadium());

    }

    @Test
    void putVerifyByStadiumAndDayTestException() {
        MatchDto matchDto = new MatchDto("Team1", "Team2", "StadiumX", LocalDateTime.now(), 3, 2);

        List<SoccerMatch> existingMatches = Arrays.asList(
                new SoccerMatch(idExisting, "TeamA", "TeamB", "StadiumX", LocalDateTime.now(), 1, 0),
                new SoccerMatch(idExisting, "TeamC", "TeamD", "StadiumX", LocalDateTime.now(), 2, 2)
        );

        when(matchRepository.findAllByStadiumEqualsIgnoreCase("StadiumX")).thenReturn(existingMatches);
        when(matchRepository.findById(idExisting)).thenReturn(Optional.of(new SoccerMatch()));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> matchServices.put(idExisting, matchDto));

        assertEquals(BAD_REQUEST, exception.getStatusCode());
        assertEquals("Já existe um jogo nesta mesma data para este estádio!", exception.getReason());

        verify(matchRepository).findAllByStadiumEqualsIgnoreCase("StadiumX");
    }
    @Test
    void putVerifyRegisterTimeTestDateNotNull() {

        MatchDto matchDtoTimeMin = new MatchDto("Team1", "Team2", "StadiumX", LocalDateTime.of(2023, 11, 20, 7, 59), 3, 2);
        MatchDto matchDtoTimeMax = new MatchDto("Team1", "Team2", "StadiumX", LocalDateTime.of(2023, 11, 20, 22, 1), 3, 2);

        when(matchRepository.findById(idExisting)).thenReturn(Optional.of(new SoccerMatch()));

        ResponseStatusException exceptionTimeMin = assertThrows(ResponseStatusException.class, () -> matchServices.put(idExisting, matchDtoTimeMin));
        ResponseStatusException exceptionTimeMax = assertThrows(ResponseStatusException.class, () -> matchServices.put(idExisting, matchDtoTimeMax));

        assertEquals(BAD_REQUEST, exceptionTimeMin.getStatusCode());
        assertEquals(BAD_REQUEST, exceptionTimeMax.getStatusCode());

    }

    @Test
    void putVerifyByDayAndTeamTestException() {
        MatchDto matchDto = new MatchDto("Team1", "Team2", "StadiumX", LocalDateTime.of(2023, 11, 22, 20, 0), 3, 2);

        List<SoccerMatch> existingMatches = List.of(
                new SoccerMatch(idExisting, "TeamC", "TeamD", "StadiumX",
                        LocalDateTime.of(2023, 11, 23, 20, 0), 2, 2)
        );

        when(matchRepository.findAllByHomeTeamOrVisitingTeamEqualsIgnoreCase(anyString(), anyString())).thenReturn(existingMatches);
        when(matchRepository.findById(idExisting)).thenReturn(Optional.of(new SoccerMatch()));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> matchServices.post(matchDto));

        assertEquals(BAD_REQUEST, exception.getStatusCode());
        assertEquals("Não é permitido uma partida com intervalo < 48h!", exception.getReason());

        verify(matchRepository).findAllByHomeTeamOrVisitingTeamEqualsIgnoreCase(anyString(), anyString());

    }


    // TESTS FOR DELETE METHOD
    @Test
    void deleteMatchTestSuccess() {
        when(matchRepository.existsById(idExisting)).thenReturn(true);
        doNothing().when(matchRepository).deleteById(idExisting);

        String response = matchServices.delete(idExisting);

        assertEquals("Partida deletada com sucesso!", response);
        verify(matchRepository).existsById(idExisting);
        verify(matchRepository).deleteById(idExisting);
        verifyNoMoreInteractions(matchRepository);
    }

    @Test
    void deleteMatchTestUnknownId() {
        when(matchRepository.existsById(idNonExisting)).thenReturn(anyBoolean());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> matchServices.delete(idNonExisting));

        assertEquals(NOT_FOUND, exception.getStatusCode());
        assertEquals("Esse id não existe!", exception.getReason());

        verify(matchRepository).existsById(idNonExisting);
        verify(matchRepository, never()).delete(any());
    }


//    TESTS FOR POST METHOD AND SUB-METHODS
    @Test
    void postMatchTestSuccess() {
        MatchDto matchDto = new MatchDto("Team1", "Team2", "StadiumX", LocalDateTime.now(), 3, 2);

        when(matchRepository.findAllByStadiumEqualsIgnoreCase("StadiumX")).thenReturn(Collections.emptyList());
        when(matchRepository.findAllByHomeTeamOrVisitingTeamEqualsIgnoreCase(anyString(), anyString())).thenReturn(Collections.emptyList());

        ResponseEntity<String> response = matchServices.post(matchDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(matchRepository).findAllByStadiumEqualsIgnoreCase("StadiumX");
        verify(matchRepository, times(2)).findAllByHomeTeamOrVisitingTeamEqualsIgnoreCase(anyString(), anyString());
        verify(matchRepository).save(any());

    }

    @Test
    void postVerifyRegisterTimeTestDateNull() {

        MatchDto matchDto = new MatchDto("Team1", "Team2", "StadiumX", null, 3, 2);

        IllegalArgumentException exception =  assertThrows(IllegalArgumentException.class, () -> matchServices.post(matchDto));

        assertEquals("A data não pode ser nula", exception.getMessage());

    }

    @Test
    void postVerifyRegisterTimeTestDateNotNull() {

        MatchDto matchDtoTimeMin = new MatchDto("Team1", "Team2", "StadiumX", LocalDateTime.of(2023, 11, 20, 7, 59), 3, 2);
        MatchDto matchDtoTimeMax = new MatchDto("Team1", "Team2", "StadiumX", LocalDateTime.of(2023, 11, 20, 22, 1), 3, 2);

        ResponseStatusException exceptionTimeMin = assertThrows(ResponseStatusException.class, () -> matchServices.post(matchDtoTimeMin));
        ResponseStatusException exceptionTimeMax = assertThrows(ResponseStatusException.class, () -> matchServices.post(matchDtoTimeMax));

        assertEquals(BAD_REQUEST, exceptionTimeMin.getStatusCode());
        assertEquals(BAD_REQUEST, exceptionTimeMax.getStatusCode());

    }

    @Test
    void postVerifyByStadiumAndDayTestSuccess() {
        MatchDto matchDto = new MatchDto("Team1", "Team2", "StadiumX", LocalDateTime.now(), 3, 2);

        List<SoccerMatch> soccerMatches = Arrays.asList(new SoccerMatch(idExisting, "TeamA", "TeamB", "StadiumX", LocalDateTime.now(), 1, 0));

        when(matchRepository.findAllByStadiumEqualsIgnoreCase(matchDto.getStadium())).thenReturn(soccerMatches);

        List<SoccerMatch> response = matchRepository.findAllByStadiumEqualsIgnoreCase(matchDto.getStadium());

        assertEquals(soccerMatches, response);

        verify(matchRepository).findAllByStadiumEqualsIgnoreCase(matchDto.getStadium());

    }

    @Test
    void postVerifyByStadiumAndDayTestException() {
        MatchDto matchDto = new MatchDto("Team1", "Team2", "StadiumX", LocalDateTime.now(), 3, 2);

        List<SoccerMatch> existingMatches = Arrays.asList(
                new SoccerMatch(idExisting, "TeamA", "TeamB", "StadiumX", LocalDateTime.now(), 1, 0),
                new SoccerMatch(idExisting, "TeamC", "TeamD", "StadiumX", LocalDateTime.now(), 2, 2)
        );

        when(matchRepository.findAllByStadiumEqualsIgnoreCase("StadiumX")).thenReturn(existingMatches);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> matchServices.post(matchDto));

        assertEquals(BAD_REQUEST, exception.getStatusCode());
        assertEquals("Já existe um jogo nesta mesma data para este estádio!", exception.getReason());

        verify(matchRepository).findAllByStadiumEqualsIgnoreCase("StadiumX");
    }

    @Test
    void postVerifyByDayAndTeamTestException() {
        MatchDto matchDto = new MatchDto("Team1", "Team2", "StadiumX", LocalDateTime.of(2023, 11, 22, 20, 0), 3, 2);

        List<SoccerMatch> existingMatches = List.of(
                new SoccerMatch(idExisting, "TeamC", "TeamD", "StadiumX",
                        LocalDateTime.of(2023, 11, 23, 20, 0), 2, 2)
        );

        when(matchRepository.findAllByHomeTeamOrVisitingTeamEqualsIgnoreCase(anyString(), anyString())).thenReturn(existingMatches);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> matchServices.post(matchDto));

        assertEquals(BAD_REQUEST, exception.getStatusCode());
        assertEquals("Não é permitido uma partida com intervalo < 48h!", exception.getReason());

        verify(matchRepository).findAllByHomeTeamOrVisitingTeamEqualsIgnoreCase(anyString(), anyString());

    }



    // TESTS FOR GET METHODS
    @Test
    void getFilteredByMatchTestSuccess() {
        List<SoccerMatch> soccerMatchList1 = List.of(new SoccerMatch(1L, "TeamA", "TeamB", "StadiumX", LocalDateTime.of(2023, 11, 20, 20, 0), 3, 2));
        List<SoccerMatch> soccerMatchList2 = List.of(new SoccerMatch(2L, "TeamA", "TeamB", "StadiumX", LocalDateTime.of(2023, 8, 20, 20, 0), 3, 0));
        List<SoccerMatch> soccerMatchList3 = List.of(new SoccerMatch(3L, "TeamA", "TeamB", "StadiumX", LocalDateTime.of(2023, 4, 20, 20, 0), 0, 0));

        when(matchRepository.findAll()).thenReturn(soccerMatchList1, soccerMatchList2, soccerMatchList3);

        String filter1 = "all";
        String filter2 = "goleada";
        String filter3 = "zero-gols";

        List<SoccerMatch> soccerMatch1 = matchServices.getFilteredByMatch(filter1);
        List<SoccerMatch> soccerMatch2 = matchServices.getFilteredByMatch(filter2);
        List<SoccerMatch> soccerMatch3 = matchServices.getFilteredByMatch(filter3);

        assertEquals(soccerMatchList1, soccerMatch1);
        assertEquals(soccerMatchList2, soccerMatch2);
        assertEquals(soccerMatchList3, soccerMatch3);

        verify(matchRepository, times(3)).findAll();

    }

    @Test
    void getFilteredByMatchTestException() {

        String filter = "unknown-filter";

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> matchServices.getFilteredByMatch(filter));

        assertEquals(NOT_FOUND, exception.getStatusCode());
        assertEquals("Partida não encontrada!", exception.getReason());

    }

    @Test
    void getAllMatchTestSuccess() {
        List<SoccerMatch> soccerMatchList = List.of(new SoccerMatch(1L, "TeamA", "TeamB", "StadiumX", LocalDateTime.of(2023, 11, 20, 20, 0), 3, 2));
        when(matchRepository.findAll()).thenReturn(soccerMatchList);

        List<SoccerMatch> response = matchServices.getAllMatch();

        assertEquals(soccerMatchList, response);

        verify(matchRepository).findAll();
    }

    @Test
    void getAllMatchTestEmptyList() {
        when(matchRepository.findAll()).thenReturn(Collections.emptyList());

        List<SoccerMatch> response = matchServices.getAllMatch();

        assertEquals(Collections.emptyList(), response);

        verify(matchRepository).findAll();
    }

    @Test
    void getByThrashedTestSuccess() {
        List<SoccerMatch> soccerMatchList = List.of(new SoccerMatch(1L, "TeamA", "TeamB", "StadiumX", LocalDateTime.of(2023, 11, 20, 20, 0), 3, 0));
        when(matchRepository.findAll()).thenReturn(soccerMatchList);

        List<SoccerMatch> response = matchServices.getByThrashed();

        assertEquals(soccerMatchList, response);

        verify(matchRepository).findAll();

    }

    @Test
    void getByThrashedTestEmptyList() {
        List<SoccerMatch> soccerMatchList = List.of(new SoccerMatch(1L, "TeamA", "TeamB", "StadiumX", LocalDateTime.of(2023, 11, 20, 20, 0), 3, 2));
        when(matchRepository.findAll()).thenReturn(soccerMatchList);

        List<SoccerMatch> response = matchServices.getByThrashed();

        assertEquals(Collections.emptyList(), response);

        verify(matchRepository).findAll();

    }

    @Test
    void getByZeroGoalsTestSuccess() {
        List<SoccerMatch> soccerMatchList = List.of(new SoccerMatch(1L, "TeamA", "TeamB", "StadiumX", LocalDateTime.of(2023, 11, 20, 20, 0), 0, 0));
        when(matchRepository.findAll()).thenReturn(soccerMatchList);

        List<SoccerMatch> response = matchServices.getByZeroGoals();

        assertEquals(soccerMatchList, response);

        verify(matchRepository).findAll();

    }

    @Test
    void getByZeroGoalsTestEmptyList() {
        List<SoccerMatch> soccerMatchList = List.of(new SoccerMatch(1L, "TeamA", "TeamB", "StadiumX", LocalDateTime.of(2023, 11, 20, 20, 0), 3, 2));
        when(matchRepository.findAll()).thenReturn(soccerMatchList);

        List<SoccerMatch> response = matchServices.getByZeroGoals();

        assertEquals(Collections.emptyList(), response);

        verify(matchRepository).findAll();

    }
}