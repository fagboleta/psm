package br.com.dfaglioni.pismo.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OperationTypeTest {

	
	@Test
	public void adjustValueCompraParcela() {
		
		
		assertEquals(OperationType.COMPRA_PARCELADA.adjustValueByOperation(BigDecimal.ONE), BigDecimal.ONE.negate());
		
	}
	
	
	
	@Test
	public void adjustValuePagamento() {
		
		assertEquals(OperationType.PAGAMENTO.adjustValueByOperation(BigDecimal.ONE), BigDecimal.ONE);
	
		
	}
	
	
	
	@Test
	public void adjustValueInvalido() {
		
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {

			OperationType.INVALIDA.adjustValueByOperation(BigDecimal.ONE);

		});
		

	}
	
	
}
