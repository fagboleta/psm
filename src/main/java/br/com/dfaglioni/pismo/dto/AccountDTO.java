package br.com.dfaglioni.pismo.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

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
public class AccountDTO {

	@JsonProperty(value =  "account_id",  access = Access.READ_ONLY)
	private Long id;

	@JsonProperty("document_number")
	@NotNull
	private Long documentNumber;
	
	@JsonProperty("available_credit_Limit")
	@NotNull
	private BigDecimal availableCreditLimit;
	
}
