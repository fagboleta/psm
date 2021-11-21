package br.com.dfaglioni.pismo.domain;

import java.math.BigDecimal;
import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OperationType {

	INVALIDA(value -> {
		throw new IllegalArgumentException();
	}), COMPRA_A_VISTA(OperationType::amountNegative), COMPRA_PARCELADA(OperationType::amountNegative),
	SAQUE(OperationType::amountNegative), PAGAMENTO(OperationType::amountPositive);

	private OperationType(Function<BigDecimal, BigDecimal> adjustValueByOperationFunction) {

		this.adjustValueByOperationFunction = adjustValueByOperationFunction;
	}

	private final Function<BigDecimal, BigDecimal> adjustValueByOperationFunction;

	public BigDecimal adjustValueByOperation(BigDecimal amount) {

		return adjustValueByOperationFunction.apply(amount);
	}

	private static BigDecimal amountNegative(BigDecimal amount) {

		return amount.negate();
	}

	private static BigDecimal amountPositive(BigDecimal amount) {

		return amount;
	}

	@JsonValue
	public int toValue() {
		return ordinal();
	}
}
