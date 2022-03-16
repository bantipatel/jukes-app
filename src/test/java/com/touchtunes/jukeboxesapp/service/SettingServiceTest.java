package com.touchtunes.jukeboxesapp.service;

import com.touchtunes.jukeboxesapp.model.Setting;
import com.touchtunes.jukeboxesapp.model.SettingsDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * This Test class is validate for SettingService.
 *
 */
@ExtendWith(MockitoExtension.class)
public class SettingServiceTest {

    private static final String SETTING_ID = "e9869bbe-887f-4d0a-bb9d-b81eb55fbf0a";

    @InjectMocks
    private SettingService sut;

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    void setUp() {
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.accept(MediaType.APPLICATION_JSON)).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(any(ParameterizedTypeReference.class)))
                .thenReturn(Mono.just(getSettingsDTO()));
    }

    @Test
    public void givenSettingIdWhenFindSettingBySettingIdThenValidSetting() {
        // Act
        Setting settingLocal = sut.findSettingBySettingId(SETTING_ID);
        // Assert
        assertEquals(settingLocal.getId(), SETTING_ID);
        assertEquals(settingLocal.getRequires()[0], "camera");
    }

    private static SettingsDTO getSettingsDTO() {
        Setting settingLocal = new Setting();
        settingLocal.setId(SETTING_ID);
        settingLocal.setRequires(new String[] {"camera", "speaker", "pcb"});
        SettingsDTO settingsDTO = new SettingsDTO();
        settingsDTO.setSettings(new Setting[] {settingLocal});
        return settingsDTO;
    }
}
