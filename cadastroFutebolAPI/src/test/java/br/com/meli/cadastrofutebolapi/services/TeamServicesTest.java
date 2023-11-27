package br.com.meli.cadastrofutebolapi.services;

import br.com.meli.cadastrofutebolapi.entities.SoccerMatch;
import br.com.meli.cadastrofutebolapi.repositories.MatchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TeamServicesTest {

    @Mock
    private MatchRepository matchRepository;

    @InjectMocks
    private TeamServices teamServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void testGetAllByTeam() {
        List<SoccerMatch> matches = new ArrayList<>();
        when(matchRepository.findAllByHomeTeamOrVisitingTeamEqualsIgnoreCase("TeamA", "TeamA")).thenReturn(matches);

        List<SoccerMatch> result = teamServices.getFilteredByTeam("buscar-por-time", "TeamA");
        assertEquals(matches, result);
    }

    @Test
    void testGetHomeTeam() {
        List<SoccerMatch> matches = new ArrayList<>();
        when(matchRepository.findAllByHomeTeamEqualsIgnoreCase("TeamA")).thenReturn(matches);

        List<SoccerMatch> result = teamServices.getFilteredByTeam("time-mandante", "TeamA");
        assertEquals(matches, result);
    }

    @Test
    void testGetVisitingTeam() {
        List<SoccerMatch> matches = new ArrayList<>();
        when(matchRepository.findAllByVisitingTeamEqualsIgnoreCase("TeamA")).thenReturn(matches);

        List<SoccerMatch> result = teamServices.getFilteredByTeam("time-visitante", "TeamA");
        assertEquals(matches, result);
    }

    @Test
    void testUnknownFilter() {
        assertThrows(ResponseStatusException.class,
                () -> teamServices.getFilteredByTeam("unknown-filter", "TeamA"));
        verifyNoInteractions(matchRepository);
    }
}