package com.digitalinnovation.cervejas.demo.controller;

import com.digitalinnovation.cervejas.demo.dto.CervejaDTO;
import com.digitalinnovation.cervejas.demo.exceptions.CervejaJaCadastradaException;
import com.digitalinnovation.cervejas.demo.exceptions.CervejaNaoCadastradaException;
import com.digitalinnovation.cervejas.demo.service.CervejaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cervejas")
public class CervejaController {

    private final CervejaService service;

    //TODO retornar nos endpoints um ResponseEntity
    @GetMapping
    public List<CervejaDTO> listaTodos() {
        return service.listaTodas();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CervejaDTO criaCerveja(@RequestBody @Valid CervejaDTO dto) throws CervejaJaCadastradaException {
        return service.salvaCerveja(dto);
    }

    @GetMapping("/{id}")
    public CervejaDTO buscaPorId(@PathVariable Long id) throws CervejaNaoCadastradaException {
        return service.listaPorId(id);
    }

    @GetMapping("/busca/{nome}")
    public CervejaDTO buscaPorNome(@PathVariable String nome) throws CervejaNaoCadastradaException {
        return service.buscaPorNome(nome);
    }

    @PutMapping
    public CervejaDTO atualizaCerveja(@RequestBody CervejaDTO dto) throws CervejaNaoCadastradaException {
        return service.atualizaCerveja(dto);
    }

    @DeleteMapping("/{id}")
    public void deletaCerveja(@PathVariable Long id) throws CervejaNaoCadastradaException {
        service.deletaCerveja(id);
    }

}
