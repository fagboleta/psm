package br.com.dfaglioni.pismo.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.com.dfaglioni.pismo.domain.OperationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class TransactionDTO {

	@JsonProperty(value =  "transaction_id", access = Access.READ_ONLY)
	private Long id;

	@JsonProperty("account_id")
	@NotNull
	private Long accountId;

	@JsonProperty("operation_type_id")
	@NotNull
	private OperationType operationType;

	@NotNull
	@DecimalMin(value = "0.0", inclusive = false)
	private BigDecimal amount;
	
	@JsonProperty( value =  "event_date", access = Access.READ_ONLY)
	private LocalDateTime eventDate;

}
