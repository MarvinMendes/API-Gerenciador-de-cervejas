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
        CervejaDTO cervejaDTOBuilder = CervejaDTOBuilder.builder().build().paraCervejaDTO();
        Cerveja cervejaEsperada = mapper.toEntity(cervejaDTOBuilder);

        //when
        when(repository.findByNome(cervejaDTOBuilder.getNome())).thenReturn(Optional.empty());
        when(repository.save(cervejaEsperada)).thenReturn(cervejaEsperada);

        //then
        CervejaDTO cervejaSalva = service.salvaCerveja(cervejaDTOBuilder);

        //assert
        assertEquals(cervejaDTOBuilder.getId(), cervejaSalva.getId());
        assertEquals(cervejaDTOBuilder.getNome(), cervejaSalva.getNome());
    }




}
