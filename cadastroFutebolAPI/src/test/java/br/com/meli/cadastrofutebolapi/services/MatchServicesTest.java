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

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MatchServicesTest {

    @Mock
    private MatchRepository matchRepository;

    @InjectMocks
    private MatchServices matchServices;

    private Long idExisting;

    private Long idNonExisting;

    private MatchDto matchDto;

    private List<SoccerMatch> soccerMatch;

    private Optional<SoccerMatch> response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        matchDto = new MatchDto("TeamA", "TeamB", "Stadium", "2023-11-02T09:04:23.485", 3, 2);

        idExisting = 1L;

        idNonExisting = 10235L;

        response = matchRepository.findById(idExisting);

    }

    // TESTS FOR PUT METHOD
    @Test
    void searchByIdToUpdateMatchSuccess() {
        when(matchRepository.findById(idExisting)).thenReturn(Optional.empty());
    }

    @Test
    void searchByIdToUpdateMatchUnknownId() {
        when(matchRepository.findById(idNonExisting)).thenReturn(Optional.empty());
    }

    @Test
    void updateMatchSuccess() {

        if (response.isPresent()) {
            SoccerMatch newMatch = response.get();

            matchDto = new MatchDto("TeamC", "TeamD", "StadiumX", "2023-12-02T09:04:23.485", 3, 2);

            when(matchRepository.save(newMatch)).thenReturn(newMatch);
            verifyNoMoreInteractions();
        }
    }

    @Test
    void searchByIdToUpdateMatchUnknownIdException() {
        assertThrows(ResponseStatusException.class, () -> matchServices.put(idNonExisting, matchDto));
    }

    // TESTS FOR DELETE METHOD
    @Test
    void searchByIdToDeleteMatchSuccess() {
        when(matchRepository.existsById(idExisting)).thenReturn(true);
    }

    @Test
    void searchByIdToDeleteMatchUnknownId() {
        when(matchRepository.existsById(idNonExisting)).thenReturn(false);
    }

    @Test
    void deleteMatchSuccess() {
        if (matchRepository.existsById(idExisting)) {

            doNothing().when(matchRepository).deleteById(idExisting);

            matchServices.delete(idExisting);

            verify(matchRepository).deleteById(idExisting);

            verifyNoMoreInteractions(matchRepository);
        }
    }

    @Test
    void searchByIdToDeleteMatchUnknownIdException() {
        assertThrows(ResponseStatusException.class, () -> matchServices.delete(idNonExisting));
    }

    // TESTS FOR POST METHOD

    @Test
    void postNewMatchReturnMessageTest() {
        SoccerMatch match = new SoccerMatch();
        when(matchRepository.save(match)).thenReturn(match);
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