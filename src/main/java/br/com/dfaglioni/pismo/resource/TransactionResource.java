package br.com.dfaglioni.pismo.resource;

import java.math.BigDecimal;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dfaglioni.pismo.domain.Account;
import br.com.dfaglioni.pismo.domain.Transaction;
import br.com.dfaglioni.pismo.dto.TransactionDTO;
import br.com.dfaglioni.pismo.repository.AccountRepository;
import br.com.dfaglioni.pismo.repository.TransactionRepository;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/transactions")
@Transactional
public class TransactionResource {

	private final TransactionRepository transactionRepository;
	private final AccountRepository accountRepository;

	@PostMapping
	public ResponseEntity<TransactionDTO> save(@RequestBody @Valid TransactionDTO transactionDTO) {

		Optional<Account> accountOptional = accountRepository.findById(transactionDTO.getAccountId());

		if (accountOptional.isEmpty()) {

			return ResponseEntity.notFound().build();
		}

		Account accountFound = accountOptional.get();

		BigDecimal adjustAmound = transactionDTO.getOperationType().adjustValueByOperation(transactionDTO.getAmount());
	
		TransactionDTO transationDTOSaved = transactionRepository.save(Transaction.builder().account(accountFound)
				.operationType(transactionDTO.getOperationType())
				.amount(adjustAmound).build())
				.toDTO();

		if (!accountFound.applyAmount(adjustAmound)) {

			return ResponseEntity.badRequest().build();
					
		}

		return ResponseEntity.ok(transationDTOSaved);
	}

}
