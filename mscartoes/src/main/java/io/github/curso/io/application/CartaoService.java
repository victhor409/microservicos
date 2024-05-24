package io.github.curso.io.application;

import io.github.curso.io.domain.Cartao;
import io.github.curso.io.infra.repository.CartaoRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class CartaoService {

    private final CartaoRepository cartaoRepository;

    public CartaoService(final CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }


    @Transactional
    public Cartao save(Cartao cartao) {
        return cartaoRepository.save(cartao);
    }

    public List<Cartao> getCartoesRendaMenorIgual(Long rendaMenorIgual) {

        var rendaBigDecimal = BigDecimal.valueOf(rendaMenorIgual);

        return cartaoRepository.findByRendaLessThanEqual(rendaBigDecimal);
    }

}
