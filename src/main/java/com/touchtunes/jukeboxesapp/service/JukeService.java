package com.touchtunes.jukeboxesapp.service;

import com.touchtunes.jukeboxesapp.model.Juke;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class JukeService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    /**
     * This method is used to get List of Juke objects.
     *
     * @return
     */
    public List<Juke> getJukes() {
        Mono<List<Juke>> jukeListResponse = webClientBuilder.build().get()
                .uri("http://my-json-server.typicode.com/touchtunes/tech-assignment/jukes")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Juke>>() {});
        return jukeListResponse != null ? jukeListResponse.block() : new ArrayList<>();
    }

}
