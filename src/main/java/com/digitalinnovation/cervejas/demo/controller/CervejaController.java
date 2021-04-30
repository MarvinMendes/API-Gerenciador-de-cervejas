package com.digitalinnovation.cervejas.demo.controller;

import com.digitalinnovation.cervejas.demo.dto.CervejaDTO;
import com.digitalinnovation.cervejas.demo.exceptions.CervejaJaCadastradaException;
import com.digitalinnovation.cervejas.demo.exceptions.CervejaNaoCadastradaException;
import com.digitalinnovation.cervejas.demo.serviceImpl.CervejaServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cervejas")
public class CervejaController {

    private final CervejaServiceImpl service;

    public CervejaController(CervejaServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public List<CervejaDTO> listaTodos() {
        return service.listaTodas();
    }

    @PostMapping
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
