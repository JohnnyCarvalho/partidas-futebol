package br.com.meli.cadastrofutebolapi.services;

import br.com.meli.cadastrofutebolapi.entities.SoccerMatch;
import br.com.meli.cadastrofutebolapi.repositories.MatchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class StadiumServicesTest {

    @Mock
    private MatchRepository matchRepository;

    @InjectMocks
    private StadiumServices stadiumServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetByStadium() {
        List<SoccerMatch> match = new ArrayList<>();
        when(matchRepository.findAllByStadiumEqualsIgnoreCase("Stadium")).thenReturn(match);
    }

    @Test
    void testUnknownFilter() {
        assertThrows(ResponseStatusException.class,
                () -> stadiumServices.getFilteredByStadium("unknown-filter", "Stadium"));
        verifyNoInteractions(matchRepository);
    }
}