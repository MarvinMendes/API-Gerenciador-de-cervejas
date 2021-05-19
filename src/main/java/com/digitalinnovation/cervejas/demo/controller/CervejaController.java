package com.digitalinnovation.cervejas.demo.controller;

import com.digitalinnovation.cervejas.demo.dto.CervejaDTO;
import com.digitalinnovation.cervejas.demo.exceptions.CervejaJaCadastradaException;
import com.digitalinnovation.cervejas.demo.exceptions.CervejaNaoCadastradaException;
import com.digitalinnovation.cervejas.demo.exceptions.EstoqueDeCervejaExcedidoException;
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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CervejaDTO> listaTodos() {
        return service.listaTodas();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CervejaDTO criaCerveja(@RequestBody @Valid CervejaDTO dto) throws CervejaJaCadastradaException {
        return service.salvaCerveja(dto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CervejaDTO buscaPorId(@PathVariable Long id) throws CervejaNaoCadastradaException {
        return service.listaPorId(id);
    }

    @GetMapping("/nome/{nome}")
    @ResponseStatus(HttpStatus.OK)
    public CervejaDTO buscaPorNome(@PathVariable String nome) throws CervejaNaoCadastradaException {
        return service.buscaPorNome(nome);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CervejaDTO atualizaCerveja(@RequestBody CervejaDTO dto) throws CervejaNaoCadastradaException {
        return service.atualizaCerveja(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deletaCerveja(@PathVariable Long id) throws CervejaNaoCadastradaException {
        service.deletaCerveja(id);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PatchMapping("/{id}/increment/{quantidade}")
    public void incrementaCerveja(@PathVariable Long id, @PathVariable int quantidade) throws EstoqueDeCervejaExcedidoException, CervejaNaoCadastradaException {
        service.incrementa(id, quantidade);
    }


}
