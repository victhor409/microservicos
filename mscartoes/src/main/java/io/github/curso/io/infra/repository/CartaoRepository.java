package io.github.curso.io.infra.repository;

import io.github.curso.io.domain.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface CartaoRepository extends JpaRepository<Cartao,Long> {

    List<Cartao> findByRendaLessThanEqual(BigDecimal renda);
}
