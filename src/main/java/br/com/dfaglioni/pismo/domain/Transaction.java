package br.com.dfaglioni.pismo.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import br.com.dfaglioni.pismo.dto.TransactionDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
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
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transaction_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "account_id")
	@NotNull
	private Account account;

	@Column(name = "operation_type_id")
	@NotNull
	private OperationType operationType;

	@NotNull
	private BigDecimal amount;

	@NotNull
	@Column(name = "event_date")
	@Default
	private LocalDateTime eventDate = LocalDateTime.now();

	public TransactionDTO toDTO() {

		return TransactionDTO.builder().id(id).accountId(account.getId()).operationType(operationType).amount(amount)
				.eventDate(eventDate).build();

	}

}
