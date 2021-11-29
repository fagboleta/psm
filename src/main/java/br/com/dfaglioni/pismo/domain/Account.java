package br.com.dfaglioni.pismo.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import br.com.dfaglioni.pismo.dto.AccountDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@EqualsAndHashCode(of = "id")
@Getter
@DynamicInsert
@DynamicUpdate
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_id")
	private Long id;

	@Column(name = "document_number")
	@NotNull
	private Long documentNumber;

	@Column(name = "available_credit_limit")
	@NotNull
	@DecimalMin(value = "0.0", inclusive = false)
	private BigDecimal availableCreditLimit;

	
	public AccountDTO toDTO() {

		return AccountDTO.builder().id(id).documentNumber(documentNumber)
				.availableCreditLimit(availableCreditLimit)
				.build();

	}


	public boolean applyAmount(BigDecimal amount) {
		
		availableCreditLimit  = availableCreditLimit.add(amount);
		//TODO CONSIDERAR ERRO DE ARRENDONDAMENTO
		return  availableCreditLimit.compareTo(BigDecimal.ZERO) >= 0; 
		
	}
}
