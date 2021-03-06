package com.digitalinnovation.cervejas.demo.mapper;

import com.digitalinnovation.cervejas.demo.dto.CervejaDTO;
import com.digitalinnovation.cervejas.demo.entities.Cerveja;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring", uses = {CervejaMapper.class})
public interface CervejaMapper {

    CervejaMapper INSTANCE = Mappers.getMapper(CervejaMapper.class);

    Cerveja toEntity(CervejaDTO dto);
    CervejaDTO toDTO(Cerveja cerveja);
    CervejaDTO toDTOByOptional(Optional<Cerveja> cerveja);
    List<CervejaDTO> toDTOList(List<Cerveja> list);
    List<Cerveja> toEntityList(List<CervejaDTO> dtoList);
}
