package com.digitalinnovation.cervejas.demo.service;

import com.digitalinnovation.cervejas.demo.builder.CervejaDTOBuilder;
import com.digitalinnovation.cervejas.demo.dto.CervejaDTO;
import com.digitalinnovation.cervejas.demo.entities.Cerveja;
import com.digitalinnovation.cervejas.demo.exceptions.CervejaJaCadastradaException;
import com.digitalinnovation.cervejas.demo.exceptions.CervejaNaoCadastradaException;
import com.digitalinnovation.cervejas.demo.exceptions.EstoqueDeCervejaExcedidoException;
import com.digitalinnovation.cervejas.demo.mapper.CervejaMapper;
import com.digitalinnovation.cervejas.demo.repository.CervejaRepository;
import com.digitalinnovation.cervejas.demo.serviceImpl.CervejaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    void quandoUmaCervejaExistenteForInformadaEntaoUmaExcecaoSeraLancada() {
        //given
        CervejaDTO cervejaEsperadaDTO = CervejaDTOBuilder.builder().build().paraCervejaDTO();
        Cerveja cervejaDuplicada = mapper.toEntity(cervejaEsperadaDTO);

        //when
        when(repository.findByNome(cervejaDuplicada.getNome())).thenReturn(Optional.of(cervejaDuplicada));

        //then
        assertThrows(CervejaJaCadastradaException.class, () -> service.salvaCerveja(cervejaEsperadaDTO));

    }

    @Test
    void quandoUmGETForInvocadoUmaListaPreenchidaDeveSerRetornada() {
        //given
        CervejaDTO cervejaDTO = CervejaDTOBuilder.builder().build().paraCervejaDTO();
        Cerveja cervejaEsperada = mapper.toEntity(cervejaDTO);


        //when
        when(repository.findAll()).thenReturn(Collections.singletonList(cervejaEsperada));

        //then
        List<CervejaDTO> listaComCervejas = service.listaTodas();

        assertThat(listaComCervejas, is(not(empty())));
        assertThat(listaComCervejas.get(0), is(equalTo(cervejaDTO)));
    }


    @Test
    void quandoUmIncrementoValidoForPassado() throws CervejaNaoCadastradaException, EstoqueDeCervejaExcedidoException {

        CervejaDTO cervejaDTO = CervejaDTOBuilder.builder().build().paraCervejaDTO();
        Cerveja cervejaEsperada = mapper.toEntity(cervejaDTO);

        //when
        when(repository.findById(cervejaDTO.getId())).thenReturn(Optional.of(cervejaEsperada));
        when(repository.save(cervejaEsperada)).thenReturn(cervejaEsperada);


        int quantidadeAIncrementar = 5;
        int quantidadeAposIncremento = cervejaDTO.getQuantidade() + quantidadeAIncrementar;

        //then
        CervejaDTO cervejaIncrementada = service.incrementa(cervejaDTO.getId(), quantidadeAIncrementar);

        assertThat(quantidadeAposIncremento, is(equalTo(cervejaIncrementada.getQuantidade())));
        assertThat(quantidadeAposIncremento, is(lessThan(cervejaDTO.getMax())));

    }

    @Test
    void quandoUmIncrementoInvalidoForPassado() {

        CervejaDTO cervejaDTO = CervejaDTOBuilder.builder().build().paraCervejaDTO();
        Cerveja cervejaEsperada = mapper.toEntity(cervejaDTO);

        //when
        when(repository.findById(cervejaDTO.getId())).thenReturn(Optional.of(cervejaEsperada));

        int quantidadeAIncrementar = 200;
        //then
        assertThrows(EstoqueDeCervejaExcedidoException.class, () -> service.incrementa(cervejaDTO.getId(), quantidadeAIncrementar));
    }

    @Test
    void quandoUmIncrementoExcedenteForPassado() {

        CervejaDTO cervejaDTO = CervejaDTOBuilder.builder().build().paraCervejaDTO();
        Cerveja cervejaEsperada = mapper.toEntity(cervejaDTO);

        //when
        when(repository.findById(cervejaDTO.getId())).thenReturn(Optional.of(cervejaEsperada));

        int quantidadeAIncrementar = 200;
        //then
        assertThrows(EstoqueDeCervejaExcedidoException.class, () -> service.incrementa(cervejaDTO.getId(), quantidadeAIncrementar));
    }

    @Test
    void quandoUmIdInformadoParaIncrementoForInvalido() throws EstoqueDeCervejaExcedidoException, CervejaNaoCadastradaException {
        Long id = 90L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CervejaNaoCadastradaException.class, () -> service.incrementa(id, 10));
    }


}
