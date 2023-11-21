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
import java.util.Collections;
import java.util.List;
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


//
//    // TESTS FOR DELETE METHOD
//    @Test
//    void searchByIdToDeleteMatchSuccess() {
//        when(matchRepository.existsById(idExisting)).thenReturn(true);
//    }
//
//    @Test
//    void searchByIdToDeleteMatchUnknownId() {
//        when(matchRepository.existsById(idNonExisting)).thenReturn(false);
//    }
//
//    @Test
//    void deleteMatchSuccess() {
//        if (matchRepository.existsById(idExisting)) {
//
//            doNothing().when(matchRepository).deleteById(idExisting);
//
//            matchServices.delete(idExisting);
//
//            verify(matchRepository).deleteById(idExisting);
//
//            verifyNoMoreInteractions(matchRepository);
//        }
//    }
//
//    @Test
//    void searchByIdToDeleteMatchUnknownIdException() {
//        assertThrows(ResponseStatusException.class, () -> matchServices.delete(idNonExisting));
//    }
//
//    // TESTS FOR POST METHOD
//
//    @Test
//    void postNewMatchReturnMessageTest() {
//        SoccerMatch match = new SoccerMatch();
//        when(matchRepository.save(match)).thenReturn(match);
//    }
//
//    @Test
//    void verifyRegisterTimeTest() {
//
//    }
//
//    @Test
//    void verifyRegisterTimeTestUnknownIdException() {
//
//    }
//
//    @Test
//    void verifyByStadiumAndDayTest() {
//
//    }
//
//    @Test
//    void verifyByDayAndTeamTest() {
//
//    }
}