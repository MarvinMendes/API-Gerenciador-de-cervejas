package com.digitalinnovation.cervejas.demo.controller;

import com.digitalinnovation.cervejas.demo.builder.CervejaDTOBuilder;
import com.digitalinnovation.cervejas.demo.dto.CervejaDTO;
import com.digitalinnovation.cervejas.demo.entities.Cerveja;
import com.digitalinnovation.cervejas.demo.exceptions.CervejaJaCadastradaException;
import com.digitalinnovation.cervejas.demo.exceptions.CervejaNaoCadastradaException;
import com.digitalinnovation.cervejas.demo.mapper.CervejaMapper;
import com.digitalinnovation.cervejas.demo.repository.CervejaRepository;
import com.digitalinnovation.cervejas.demo.serviceImpl.CervejaServiceImpl;
import org.junit.jupiter.api.Assertions;
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

import java.util.Optional;

import static com.digitalinnovation.cervejas.demo.utils.JsonConversorUtils.asJsonToString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CervejaControllerTeste {

    private static final String URL_API_PATH = "/cervejas";
    private static final String URL_API_PATH_DELETE = "/cervejas/5";
    private MockMvc mockMvc;

    @InjectMocks
    private CervejaController cervejaController;

    @InjectMocks
    private CervejaMapper mapper = CervejaMapper.INSTANCE;

    @Mock
    private CervejaRepository repository;
    @InjectMocks
    private CervejaServiceImpl service;

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
        //ent??o da uma olhada e uma estudada nos imports staticos l?? em cima ???
        mockMvc.perform(post(URL_API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonToString(cervejaDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(cervejaDTO.getId()))
                .andExpect(jsonPath("$.nome").value(cervejaDTO.getNome()))
                .andExpect(jsonPath("$.tipoCerveja").value(cervejaDTO.getTipoCerveja().toString()))
                .andExpect(jsonPath("$.max").value(greaterThan(19)));
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

    @Test
    void quandoUmDeleteForInvocadoComUmIdValidoUmaCervejaSeraDeletada() throws Exception {
        mockMvc.perform(delete(URL_API_PATH_DELETE))
                .andExpect(status().isAccepted());
    }

    @Test
    void quandoUmNomeValidoForPassadoEntaoUmaCervejaDeveSerRetornada() throws Exception {

        //given
        CervejaDTO cervejaEncontradaDTO = CervejaDTOBuilder.builder().build().paraCervejaDTO();
        Cerveja cervejaEncontrada = mapper.toEntity(cervejaEncontradaDTO);

        //when
        when(repository.findByNome(cervejaEncontrada.getNome())).thenReturn(Optional.of(cervejaEncontrada));

        //then
        CervejaDTO cervejaDTO = service.buscaPorNome(cervejaEncontradaDTO.getNome());

        //assert
        assertThat(cervejaDTO, is(equalTo(cervejaEncontradaDTO)));

    }

    @Test
    void quandoUmaCervejaJaCadastradaForInformadaEntaoUmaExcecaoSeraLancada() throws Exception {
        //given
        CervejaDTO cervejaACadastrar = CervejaDTOBuilder.builder().build().paraCervejaDTO();
        Cerveja cervejaDuplicada = mapper.toEntity(cervejaACadastrar);

        //when
        when(repository.findByNome(cervejaACadastrar.getNome())).thenReturn(Optional.of(cervejaDuplicada));

        //assert
        Assertions.assertThrows(CervejaJaCadastradaException.class, () -> service.salvaCerveja(cervejaACadastrar));

    }

    @Test//ok
    void quandoUmNomeForInformadoParaBuscaENaoForEncontradoEntaoUmaExcecaoSeraLancada() throws Exception {
        //given
        CervejaDTO cervejaDTO = CervejaDTOBuilder.builder().build().paraCervejaDTO();
        Cerveja cerveja = mapper.toEntity(cervejaDTO);
        cerveja.setNome("SKOL");

        //when
        when(repository.findByNome(cerveja.getNome())).thenReturn(Optional.empty());

        //assertThrow
        Assertions.assertThrows(CervejaNaoCadastradaException.class, () -> service.buscaPorNome(cerveja.getNome()));

        mockMvc.perform(get(URL_API_PATH + "/" + cerveja.getNome()))
                .andExpect(status().isBadRequest());

    }

//um m??todo mais simples pra se converter um json em string,
// mas acabei optando por usar uma classe com m??todo statico para manter o padr??o de outros m??todos

/*    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/
}
