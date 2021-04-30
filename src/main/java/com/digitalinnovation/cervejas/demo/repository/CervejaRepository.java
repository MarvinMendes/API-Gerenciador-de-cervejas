package com.digitalinnovation.cervejas.demo.repository;

import com.digitalinnovation.cervejas.demo.entities.Cerveja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CervejaRepository extends JpaRepository<Cerveja, Long> {

    Optional<Cerveja> findByNome(String nome);

}
