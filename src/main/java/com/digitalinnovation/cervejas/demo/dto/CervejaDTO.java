package com.digitalinnovation.cervejas.demo.dto;

import com.digitalinnovation.cervejas.demo.enums.TipoCerveja;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CervejaDTO {

    private Long id;
    @NotNull
    private String nome;
    @NotNull
    private String marca;
    @NotNull
    private Integer max;
    @NotNull
    private Integer quantidade;
    @NotNull
    private TipoCerveja tipoCerveja;

}
