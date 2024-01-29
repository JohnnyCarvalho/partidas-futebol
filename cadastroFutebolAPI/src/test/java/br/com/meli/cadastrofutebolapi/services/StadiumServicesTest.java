package br.com.meli.cadastrofutebolapi.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

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
    void testGetFilteredByStadiumSuccess() {
        String filter = "buscar-por-estadio";
        String stadium = "StadiumX";
        List<SoccerMatch> match = List.of(new SoccerMatch(1L, "TeamA", "TeamB", "StadiumX", LocalDateTime.now(), 1, 0));
        when(matchRepository.findAllByStadiumEqualsIgnoreCase(stadium)).thenReturn(match);

        List<SoccerMatch> matchList = stadiumServices.getFilteredByStadium(filter, stadium);

        assertEquals(match, matchList);

        verify(matchRepository).findAllByStadiumEqualsIgnoreCase(stadium);
    }

    @Test
    void testGetByStadiumUnknownFilter() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> stadiumServices.getFilteredByStadium("unknown-filter", "Stadium"));

        assertEquals(NOT_FOUND, exception.getStatusCode());
        assertEquals("Filtro de busca não encontrado!", exception.getReason());
    }
}