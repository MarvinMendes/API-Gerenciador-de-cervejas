package com.digitalinnovation.cervejas.demo.controller;

import com.digitalinnovation.cervejas.demo.builder.CervejaDTOBuilder;
import com.digitalinnovation.cervejas.demo.dto.CervejaDTO;
import com.digitalinnovation.cervejas.demo.service.CervejaService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import static com.digitalinnovation.cervejas.demo.utils.JsonConversorUtils.asJsonToString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CervejaControllerTeste {

    private static final String URL_API_PATH = "/cervejas";
    private MockMvc mockMvc;

    @InjectMocks
    private CervejaController cervejaController;

    @Mock
    private CervejaService service;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(cervejaController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void quandoUmPOSTForInvocadoUmaCervejaDeveSerCriada() throws Exception {
        //given
        CervejaDTO cervejaDTO = CervejaDTOBuilder.builder().build().paraCervejaDTO();

        //when
        when(service.salvaCerveja(cervejaDTO)).thenReturn(cervejaDTO);

        //then
        //aqui tive que fazer trocentos imports staticos pra ficar uma leitura mais fluente - "menos verboso"
        //então da uma olhada e uma estudada nos imports staticos lá em cima ↑
        mockMvc.perform(post(URL_API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonToString(cervejaDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(cervejaDTO.getId()))
                .andExpect(jsonPath("$.nome").value(cervejaDTO.getNome()))
                .andExpect(jsonPath("$.tipoCerveja").value(cervejaDTO.getTipoCerveja().toString()))
                .andExpect(jsonPath("$.max").value(Matchers.greaterThan(19)));
    }

    @Test
    void quandoUmCampoNaoEInformadoUmErroERetornado() throws Exception {
        CervejaDTO cervejaDTO = CervejaDTOBuilder.builder().build().paraCervejaDTO();
        cervejaDTO.setMarca(null);

        mockMvc.perform(post(URL_API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonToString(cervejaDTO)))
                .andExpect(status().isBadRequest());
    }


//um método mais simples pra se converter um json em string,
// mas acabei optando por usar uma classe com método statico para manter o padrão de outros métodos

/*    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/
}
