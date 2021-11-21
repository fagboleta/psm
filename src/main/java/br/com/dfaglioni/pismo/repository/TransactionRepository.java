package br.com.dfaglioni.pismo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.dfaglioni.pismo.domain.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

}
