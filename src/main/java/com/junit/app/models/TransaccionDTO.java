package com.junit.app.models;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Brando Tomala
 * @version 1.0
 * @since 31/10/2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransaccionDTO {
    private Long cuentaOrigen;
    private Long cuentaDestino;
    private BigDecimal monto;
    private Long bancoId;
}
