package io.github.curso.io.domain;

import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;


public enum BandeiraCartao {

    MASTERCARD,
    VISA

}
