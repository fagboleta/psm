package br.com.dfaglioni.pismo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import br.com.dfaglioni.pismo.domain.Account;

@DataJpaTest
@ActiveProfiles("test")
@Sql(statements = "insert into account values (1,123456)")
public class AccountRepositoryTest {

	@Autowired
	private AccountRepository accountRepository;

	@Test
	public void findOne() {

		Optional<Account> accountOptional = accountRepository.findById(1L);

		assertTrue(accountOptional.isPresent(), "account is present");

		accountOptional.ifPresent(account -> {

			assertEquals(account.getId(), 1L, "id");

			assertEquals(account.getDocumentNumber(), 123456L, "documentoNumber");

		});

	}

	@Test
	public void save() {

		Account accountSaved = accountRepository.save(Account.builder().documentNumber(12345678900L).build());

		assertNotNull(accountSaved, "account");

		assertNotNull(accountSaved.getId(), "id");

	}

	@Test
	public void saveFailWithSameDocumentNumber() {

		Assertions.assertThrows(DataIntegrityViolationException.class, () -> {

			accountRepository.save(Account.builder().documentNumber(123456L).build());

		});

	}
	
	@Test
	public void existsByDocumentNumber() {
		
		assertTrue(accountRepository.existsByDocumentNumber(123456L),"exists");
		
	}
}
