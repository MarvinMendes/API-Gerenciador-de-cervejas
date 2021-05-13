package com.digitalinnovation.cervejas.demo.service;

import com.digitalinnovation.cervejas.demo.builder.CervejaDTOBuilder;
import com.digitalinnovation.cervejas.demo.dto.CervejaDTO;
import com.digitalinnovation.cervejas.demo.entities.Cerveja;
import com.digitalinnovation.cervejas.demo.exceptions.CervejaJaCadastradaException;
import com.digitalinnovation.cervejas.demo.mapper.CervejaMapper;
import com.digitalinnovation.cervejas.demo.repository.CervejaRepository;
import com.digitalinnovation.cervejas.demo.serviceImpl.CervejaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CervejaServiceTeste {

    @Mock
    private CervejaRepository repository;

    private CervejaMapper mapper = CervejaMapper.INSTANCE;

    @InjectMocks
    private CervejaServiceImpl service;


    @Test
    void quandoUmaCervejaForPassadaEntaoUmaCervejaDeveSerCriada() throws CervejaJaCadastradaException {

        //given
        CervejaDTO cervejaDTO = CervejaDTOBuilder.builder().build().paraCervejaDTO();
        Cerveja cervejaEsperada = mapper.toEntity(cervejaDTO);

        //when
        when(repository.findByNome(cervejaDTO.getNome())).thenReturn(Optional.empty());
        when(repository.save(cervejaEsperada)).thenReturn(cervejaEsperada);

        //then
        CervejaDTO cervejaSalva = service.salvaCerveja(cervejaDTO);

        //assert
        assertEquals(cervejaDTO.getId(), cervejaSalva.getId());
        assertEquals(cervejaDTO.getNome(), cervejaSalva.getNome());

        //utilizano o hamcrest
        assertThat(cervejaSalva.getId(), is(equalTo(cervejaDTO.getId())));
        assertThat(cervejaSalva.getNome(), is(equalTo(cervejaDTO.getNome())));
        assertThat(cervejaSalva.getQuantidade(), is(greaterThan(5)));
    }




}
