package br.com.dfaglioni.pismo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.dfaglioni.pismo.domain.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long > {

	boolean existsByDocumentNumber(Long documentNumber);
	
}
