package com.touchtunes.jukeboxesapp.service;

import com.touchtunes.jukeboxesapp.model.Setting;
import com.touchtunes.jukeboxesapp.model.SettingsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Service
public class SettingService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    /**
     * This method is used to get valid Setting object with data.
     *
     * @param settingId
     * @return
     */
    public Setting findSettingBySettingId(String settingId) {

        Mono<SettingsDTO> settingsDTOResponse = webClientBuilder.build().get()
                .uri("http://my-json-server.typicode.com/touchtunes/tech-assignment/settings")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<SettingsDTO>() {});

        SettingsDTO settingsDTO = settingsDTOResponse.block();

        List<Setting> settingList = Arrays.asList(settingsDTO.getSettings());
        return settingList.stream()
                .filter(setting -> setting.getId().equals(settingId))
                .findFirst().get();
    }
}
