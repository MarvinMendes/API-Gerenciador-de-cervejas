package com.digitalinnovation.cervejas.demo.entities;

import com.digitalinnovation.cervejas.demo.enums.TipoCerveja;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Cerveja implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private Integer max;

    @Column(nullable = false)
    private Integer quantidade;

    @Enumerated
    private TipoCerveja tipoCerveja;
}
