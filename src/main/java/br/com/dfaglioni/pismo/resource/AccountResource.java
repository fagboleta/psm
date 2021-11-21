package br.com.dfaglioni.pismo.resource;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dfaglioni.pismo.domain.Account;
import br.com.dfaglioni.pismo.dto.AccountDTO;
import br.com.dfaglioni.pismo.repository.AccountRepository;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/accounts")
@Transactional
public class AccountResource {

	private final AccountRepository accountRepository;

	@GetMapping(value = { "/{accountId}" })
	public ResponseEntity<AccountDTO> getOne(@PathVariable(name = "accountId") Long accountId) {

		Optional<Account> accountOptional = accountRepository.findById(accountId);

		if (accountOptional.isPresent()) {

			return ResponseEntity.ok(accountOptional.get().toDTO());

		}

		return ResponseEntity.notFound().build();

	}

	@PostMapping
	public ResponseEntity<AccountDTO> save(@RequestBody @Valid AccountDTO accountDTO) {

		if (accountRepository.existsByDocumentNumber(accountDTO.getDocumentNumber())) {
			
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
			
		}
		
		
		return ResponseEntity.ok(accountRepository
				.save(Account.builder().documentNumber(accountDTO.getDocumentNumber()).build()).toDTO());
	}

}
