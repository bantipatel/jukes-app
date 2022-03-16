package com.touchtunes.jukeboxesapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.touchtunes.jukeboxesapp.model.Component;
import com.touchtunes.jukeboxesapp.model.Juke;
import com.touchtunes.jukeboxesapp.model.Setting;
import com.touchtunes.jukeboxesapp.service.JukeService;
import com.touchtunes.jukeboxesapp.service.SettingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This test class is validate for JukeBoxController.
 *
 */
//@ExtendWith(MockitoExtension.class)
//@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class JukeBoxControllerTest {

    private static final String SETTING_ID = "e9869bbe-887f-4d0a-bb9d-b81eb55fbf0a";
    private static final String JUKE_ID = "5ca94a8ac470d3e47cd4713c";
    private static final String MODEL = "fusion";
    private static final String NAME = "pcb";
    private static final String JUKES_JSON_PATH = "src/test/resources/json/jukes.json";
    private static final String SETTING_NOT_FOUND_JSON_PATH = "src/test/resources/json/settingNotFoundResponse.json";
    private static final String SETTING_ID_EMPTY_JSON_PATH = "src/test/resources/json/settingIdEmptyResponse.json";
    private static final String JUKE_DATA_EMPTY_JSON_PATH = "src/test/resources/json/jukeDataNotFoundResponse.json";

    @InjectMocks
    private JukeBoxController sut;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JukeService jukeService;

    @MockBean
    private SettingService settingService;

    @BeforeEach
    public void setUp() {
        Setting setting = new Setting();
        setting.setId(SETTING_ID);
        setting.setRequires(new String[] {"camera", "speaker", "pcb"});
        when(settingService.findSettingBySettingId(any())).thenReturn(setting);
        Juke juke = new Juke();
        juke.setId(JUKE_ID);
        juke.setModel(MODEL);
        Component component = new Component();
        component.setName(NAME);
        juke.setComponents(new Component[] {component});
        when(jukeService.getJukes()).thenReturn(Arrays.asList(juke));
    }

    @Test
    void givenSettingIdWhenGetJukeBoxesThenReturnJukeResponse() throws Exception {
        mockMvc.perform(get("/jukeboxes")
                .param("settingId", SETTING_ID))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        loadFromJsonFile(Paths.get(JUKES_JSON_PATH))));
    }

    @Test
    void givenSettingIdAndModelWhenGetJukeBoxesThenReturnJukeResponse() throws Exception {
        mockMvc.perform(get("/jukeboxes")
                        .param("settingId", SETTING_ID)
                        .param("model", MODEL))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        loadFromJsonFile(Paths.get(JUKES_JSON_PATH))));
    }

    @Test
    void givenSettingIdAndModelAndOffsetAndLimitWhenGetJukeBoxesThenReturnJukeResponse() throws Exception {
        mockMvc.perform(get("/jukeboxes")
                        .param("settingId", SETTING_ID)
                        .param("model", MODEL)
                        .param("offset", String.valueOf(0))
                        .param("limit", String.valueOf(1)))
                .andExpect(status().isOk())
                .andExpect(content().json(loadFromJsonFile(Paths.get(JUKES_JSON_PATH))));
    }

    @Test
    void givenInvalidParamWhenGetJukeBoxesThenReturnJukeResponse() throws Exception {
        mockMvc.perform(get("/jukeboxes")
                        .param("settingId1", SETTING_ID))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenSettingIdWhenGetJukeBoxesThenReturnResourceNotFound() throws Exception {
        when(settingService.findSettingBySettingId(any())).thenReturn(null);
        mockMvc.perform(get("/jukeboxes")
                        .param("settingId", SETTING_ID))
                .andExpect(status().isNotFound())
                .andExpect(content().json(loadFromJsonFile(Paths.get(SETTING_NOT_FOUND_JSON_PATH))));
    }

    @Test
    void givenSettingIdIsEmptyWhenGetJukeBoxesThenReturnResourceNotFound() throws Exception {
        mockMvc.perform(get("/jukeboxes")
                        .param("settingId", ""))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(loadFromJsonFile(Paths.get(SETTING_ID_EMPTY_JSON_PATH))));
    }


    @Test
    void givenSettingIdAndJukesIsNullWhenGetJukeBoxesThenReturnResourceNotFound() throws Exception {
        when(jukeService.getJukes()).thenReturn(null);
        mockMvc.perform(get("/jukeboxes")
                        .param("settingId", SETTING_ID))
                .andExpect(status().isNotFound())
                .andExpect(content().json(loadFromJsonFile(Paths.get(JUKE_DATA_EMPTY_JSON_PATH))));
    }

    private static String loadFromJsonFile(Path path) {
        final String expectedResultFileName = path.getFileName().toString();
        try {
            final Path expectedResultPath = path.getParent().resolve(expectedResultFileName);
            return new String(Files.readAllBytes(expectedResultPath));
        }
        catch (Exception e) {
            return null;
        }
    }
}
