package com.digitalinnovation.cervejas.demo.service;

import com.digitalinnovation.cervejas.demo.dto.CervejaDTO;
import com.digitalinnovation.cervejas.demo.entities.Cerveja;
import com.digitalinnovation.cervejas.demo.exceptions.CervejaJaCadastradaException;
import com.digitalinnovation.cervejas.demo.exceptions.CervejaNaoCadastradaException;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CervejaService {
    CervejaDTO salvaCerveja(CervejaDTO cerveja) throws CervejaJaCadastradaException;
    List<CervejaDTO> listaTodas();
    CervejaDTO listaPorId(Long id) throws CervejaNaoCadastradaException;
    void deletaCerveja(Long id) throws CervejaNaoCadastradaException;
    CervejaDTO atualizaCerveja(CervejaDTO cerveja) throws CervejaNaoCadastradaException;
    CervejaDTO buscaPorNome(String nome) throws CervejaJaCadastradaException, CervejaNaoCadastradaException;
}
