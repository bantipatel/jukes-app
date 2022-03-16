package com.touchtunes.jukeboxesapp.service;

import com.touchtunes.jukeboxesapp.model.Component;
import com.touchtunes.jukeboxesapp.model.Juke;
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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

/**
 * This Test class is validate for JukeService.
 *
 */
@ExtendWith(MockitoExtension.class)
public class JukeServiceTest {

    private static final String JUKE_ID = "5ca94a8ac470d3e47cd4713c";
    private static final String MODEL = "fusion";
    private static final String NAME = "pcb";

    @InjectMocks
    private JukeService sut;

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
                .thenReturn(Mono.just(Arrays.asList(getJuke())));
    }

    @Test
    public void givenRequestWhenGetJukesThenValidJukeList() {
        // Act
        List<Juke> jukeList = sut.getJukes();
        // Assert
        Juke jukeLocal = jukeList.get(0);
        assertEquals(jukeLocal.getId(), JUKE_ID);
        assertEquals(jukeLocal.getModel(), MODEL);
        assertTrue(jukeLocal.getComponents().length == 1);
        assertEquals(jukeLocal.getComponents()[0].getName(), NAME);
    }

    public static Juke getJuke() {
        Juke juke = new Juke();
        juke.setId(JUKE_ID);
        juke.setModel(MODEL);
        Component component = new Component();
        component.setName(NAME);
        juke.setComponents(new Component[] {component});
        return juke;
    }
}
