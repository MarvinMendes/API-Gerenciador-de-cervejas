package com.digitalinnovation.cervejas.demo.serviceImpl;

import com.digitalinnovation.cervejas.demo.dto.CervejaDTO;
import com.digitalinnovation.cervejas.demo.entities.Cerveja;
import com.digitalinnovation.cervejas.demo.exceptions.CervejaJaCadastradaException;
import com.digitalinnovation.cervejas.demo.exceptions.CervejaNaoCadastradaException;
import com.digitalinnovation.cervejas.demo.mapper.CervejaMapper;
import com.digitalinnovation.cervejas.demo.repository.CervejaRepository;
import com.digitalinnovation.cervejas.demo.service.CervejaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CervejaServiceImpl implements CervejaService {

    @Autowired
    private CervejaRepository cervejaRepository;

    private final CervejaMapper cervejaMapper = CervejaMapper.INSTANCE;

    @Override
    public CervejaDTO salvaCerveja(CervejaDTO dto) throws CervejaJaCadastradaException {
        verificaSeJaFoiCadastradaPeloNome(dto.getNome());
        Cerveja cervejaSalva = cervejaRepository.save(cervejaMapper.toEntity(dto));
        return cervejaMapper.toDTO(cervejaSalva);
    }

    @Override
    public List<CervejaDTO> listaTodas() {
        return cervejaRepository.findAll()
                .stream().map(cervejaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CervejaDTO listaPorId(Long id) throws CervejaNaoCadastradaException {
        Cerveja cervejaRecuperada = verificaSeExiste(id);
        return cervejaMapper.toDTO(cervejaRecuperada);
    }

    @Override
    public void deletaCerveja(Long id) throws CervejaNaoCadastradaException {
        Cerveja cervejaRecuperada = verificaSeExiste(id);
        cervejaRepository.delete(cervejaRecuperada);
    }

    @Override
    public CervejaDTO atualizaCerveja(CervejaDTO atualiza) throws CervejaNaoCadastradaException {
        Cerveja cervejaRecuperadaEAtualizada = verificaSeExiste(atualiza.getId());
        cervejaRecuperadaEAtualizada = cervejaMapper.toEntity(atualiza);
        return cervejaMapper.toDTO(cervejaRepository.save(cervejaRecuperadaEAtualizada));
    }

    @Override
    public CervejaDTO buscaPorNome(String nome) throws CervejaNaoCadastradaException {
        Cerveja cerveja = cervejaRepository.findByNome(nome).orElseThrow(() -> new CervejaNaoCadastradaException(nome));
        return cervejaMapper.toDTO(cerveja);
    }

    private Cerveja verificaSeExiste(Long id) throws CervejaNaoCadastradaException {
        return cervejaRepository.findById(id).orElseThrow(() -> new CervejaNaoCadastradaException(id));
    }

    private Optional<Cerveja> verificaSeJaFoiCadastradaPeloNome(String nome) throws CervejaJaCadastradaException {
        Optional<Cerveja> cervejaRecuperadaPorNome = cervejaRepository.findByNome(nome);
        if (cervejaRecuperadaPorNome.isPresent()) {
            throw new CervejaJaCadastradaException(nome);
        }
        return cervejaRecuperadaPorNome;
    }

}
