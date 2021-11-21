package br.com.dfaglioni.pismo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import br.com.dfaglioni.pismo.domain.Account;
import br.com.dfaglioni.pismo.domain.OperationType;
import br.com.dfaglioni.pismo.domain.Transaction;

@DataJpaTest
@ActiveProfiles("test")
@Sql(statements = { "insert into account values (1,123456)",
		"insert into transaction values(10,1, 4, 50.44, {ts '2021-09-17 18:47:52.69'})" })
public class TransactionRepositoryTest {

	@Autowired
	private TransactionRepository transactionRepository;

	@Test
	public void findOne() {

		Optional<Transaction> transactionOptional = transactionRepository.findById(10L);

		assertTrue(transactionOptional.isPresent(), " transaction is present");

		transactionOptional.ifPresent(transaction -> {

			assertEquals(transaction.getId(), 10L, "id");

			assertEquals(transaction.getAccount().getId(), 1L, "account id");

			assertEquals(transaction.getOperationType(), OperationType.PAGAMENTO, "operation_type");

			assertEquals(transaction.getAmount(), BigDecimal.valueOf(50.44), "amount");

			assertEquals(transaction.getEventDate(), LocalDateTime.of(2021, 9, 17, 18, 47, 52, 690000000), "amount");

		});

	}

	@Test
	public void save() {

		Transaction transactionSaved = transactionRepository.save(Transaction.builder().account(Account.builder().id(1L).build())
				.operationType(OperationType.COMPRA_A_VISTA).amount(BigDecimal.TEN).build());

		assertNotNull(transactionSaved.getId(),"id");

		assertNotNull(transactionSaved.getEventDate(),"id");
		
		assertEquals(transactionSaved.getOperationType(), OperationType.COMPRA_A_VISTA, "operation_type");

		

	}

}
