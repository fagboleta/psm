package br.com.dfaglioni.pismo.resource;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.dfaglioni.pismo.domain.Account;
import br.com.dfaglioni.pismo.dto.AccountDTO;
import br.com.dfaglioni.pismo.repository.AccountRepository;

public class AccountResourceTest {

	private MockMvc mockMvc;

	private AccountResource resource;

	private AccountRepository accountRepository = mock(AccountRepository.class);

	private ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	public void setup() {

		resource = new AccountResource(accountRepository);

		mockMvc = MockMvcBuilders.standaloneSetup(resource).build();

		Account account = Account.builder().id(1L).documentNumber(12345678900L).build();
	
		when(accountRepository.findById(Mockito.eq(1L))).thenReturn(Optional.of(account));

		when(accountRepository.save(Mockito.any())).thenReturn(account);

		when(accountRepository.existsByDocumentNumber(Mockito.eq(123L))).thenReturn(true);

		
	}

	@Test
	public void getAccount() throws Exception {

		mockMvc.perform(get("/accounts/1").accept(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk()).andExpect(jsonPath("$.account_id").value(1))
				.andExpect(jsonPath("$.document_number").value(12345678900L));

	}

	@Test
	public void getAccountNotFound() throws Exception {

		mockMvc.perform(get("/accounts/2").accept(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print())
				.andExpect(status().isNotFound());

	}

	@Test
	public void postAccount() throws Exception {

		mockMvc.perform(post("/accounts").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(AccountDTO.builder().documentNumber(12345678900L).build())))
				.andDo(MockMvcResultHandlers.print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.account_id").value(1))
				.andExpect(jsonPath("$.document_number").value(12345678900L));

	}

	@Test
	public void postAccountInvalid() throws Exception {

		mockMvc.perform(post("/accounts").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(AccountDTO.builder().build())))
				.andDo(MockMvcResultHandlers.print()).andExpect(status().isBadRequest());
	
	}
	
	@Test
	public void postAccountConflict() throws Exception {

		mockMvc.perform(post("/accounts").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(AccountDTO.builder().documentNumber(123L).build())))
				.andDo(MockMvcResultHandlers.print()).andExpect(status().isConflict());
	}
}
