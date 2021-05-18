package com.digitalinnovation.cervejas.demo.builder;

import com.digitalinnovation.cervejas.demo.dto.CervejaDTO;
import com.digitalinnovation.cervejas.demo.enums.TipoCerveja;
import lombok.Builder;

@Builder
public class CervejaDTOBuilder {

    @Builder.Default
    private final Long id = 6L;

    @Builder.Default
    private String nome = "Rancor";

    @Builder.Default
    private String marca = "Krug Bier";

    @Builder.Default
    private Integer max = 200;

    @Builder.Default
    private Integer quatidade = 120;

    @Builder.Default
    private TipoCerveja tipoCerveja = TipoCerveja.INDIAPALEALE;


    public CervejaDTO paraCervejaDTO() {
        return new CervejaDTO(id,
            nome,
            marca,
            max,
            quatidade,
            tipoCerveja);
    }

}
