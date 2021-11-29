package br.com.dfaglioni.pismo.resource;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.dfaglioni.pismo.domain.Account;
import br.com.dfaglioni.pismo.domain.OperationType;
import br.com.dfaglioni.pismo.domain.Transaction;
import br.com.dfaglioni.pismo.dto.TransactionDTO;
import br.com.dfaglioni.pismo.repository.AccountRepository;
import br.com.dfaglioni.pismo.repository.TransactionRepository;

public class TransactionResourceTest {

	private MockMvc mockMvc;

	private TransactionResource resource;

	private ObjectMapper objectMapper = new ObjectMapper();

	private TransactionRepository transactionRepository = mock(TransactionRepository.class);

	private AccountRepository accountRepository = mock(AccountRepository.class);

	private Account account;

	@BeforeEach
	public void setup() {

		resource = new TransactionResource(transactionRepository, accountRepository);

		mockMvc = MockMvcBuilders.standaloneSetup(resource)
				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();

		when(transactionRepository.save(Mockito.any()))
				.thenReturn(Transaction.builder().id(10L).account(Account.builder().id(1L).build())
						.operationType(OperationType.COMPRA_A_VISTA).amount(BigDecimal.valueOf(-100)).build());

		 account = Account.builder().id(1L).documentNumber(12345678900L)
				.availableCreditLimit(BigDecimal.valueOf(1000))
				.build();

		when(accountRepository.findById(Mockito.eq(1L))).thenReturn(Optional.of(account));

	}

	@Test
	public void postTransactionNotFoundAccount() throws Exception {

		mockMvc.perform(post("/transactions").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(TransactionDTO.builder().accountId(2L)
						.operationType(OperationType.PAGAMENTO).amount(BigDecimal.valueOf(123.45)).build())))
				.andDo(MockMvcResultHandlers.print()).andExpect(status().isNotFound());
	}

	@Test
	public void postTransaction() throws Exception {

		mockMvc.perform(post("/transactions").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(TransactionDTO.builder().accountId(1L)
						.operationType(OperationType.COMPRA_A_VISTA).amount(BigDecimal.valueOf(100)).build())))
				.andDo(MockMvcResultHandlers.print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.transaction_id").value(10)).andExpect(jsonPath("$.account_id").value(1))
				.andExpect(jsonPath("$.operation_type_id").value(1)).andExpect(jsonPath("$.amount").value(-100))
				.andExpect(jsonPath("$.event_date").exists());

		
		assertTrue(account.getAvailableCreditLimit().equals(BigDecimal.valueOf(900)));

		
	}

	@Test
	public void postTransactionInvalidBody() throws Exception {

		mockMvc.perform(post("/transactions").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(
						TransactionDTO.builder().accountId(2L).amount(BigDecimal.valueOf(123.45)).build())))
				.andDo(MockMvcResultHandlers.print()).andExpect(status().isBadRequest());
	}
	
	
	@Test
	public void postTransactionLimitFail() throws Exception {

		mockMvc.perform(post("/transactions").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(TransactionDTO.builder().accountId(1L)
						.operationType(OperationType.COMPRA_A_VISTA).amount(BigDecimal.valueOf(1100)).build())))
				.andDo(MockMvcResultHandlers.print()).andExpect(status().isBadRequest());

		
	
		
	}

}
