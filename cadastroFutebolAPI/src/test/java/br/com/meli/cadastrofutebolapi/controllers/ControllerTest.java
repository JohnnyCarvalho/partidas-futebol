package br.com.meli.cadastrofutebolapi.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.meli.cadastrofutebolapi.dto.MatchDto;
import br.com.meli.cadastrofutebolapi.services.StadiumServices;
import br.com.meli.cadastrofutebolapi.services.TeamServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ControllerTest {

    private String json;

    private MatchDto matchDto;

    private String url;

    private List<SoccerMatch> soccerMatchList;

    @Mock
    private MatchServices matchServices;

    @Mock
    private TeamServices teamServices;

    @Mock
    private StadiumServices stadiumServices;

    @InjectMocks
    private Controller controller;

    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    MatchRepository matchRepository;


    @BeforeEach
    void setUp() throws JsonProcessingException {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .alwaysDo(print())
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        url = "/partida";
        matchDto = MatchDto.builder().homeTeam("Flamengo").visitingTeam("Gremio").stadium("Maracana").date(LocalDateTime.now()).goalsHomeTeam(3).goalsVisitingTeam(1).build();
        SoccerMatch match = new SoccerMatch(1L, "TeamC", "TeamD", "StadiumX",
                LocalDateTime.of(2023, 11, 23, 20, 0), 2, 2);
        matchRepository.save(match);
        json = objectMapper.writeValueAsString(matchDto);
        controller.postMatch(matchDto);
    }

    @AfterEach
    void down() {
        matchRepository.deleteAll();
    }



    // TESTS FOR GET METHODS
    @Test
    void postMatchTestSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

}