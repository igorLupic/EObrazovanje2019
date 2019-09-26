package eObrazovanje.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import eObrazovanje.Model.Transaction;



public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
