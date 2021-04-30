package com.digitalinnovation.cervejas.demo.dto;

import com.digitalinnovation.cervejas.demo.enums.TipoCerveja;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CervejaDTO {

    private Long id;

    private String nome;
    private String marca;
    private Integer max;
    private Integer quantidade;
    private TipoCerveja tipoCerveja;




}
