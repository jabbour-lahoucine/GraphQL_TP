package ma.projet.graph.repositories;


import ma.projet.graph.entities.Compte;
import ma.projet.graph.entities.Transaction;

import ma.projet.graph.entities.TypeTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByCompte(Compte compte);

    @Query("SELECT SUM(t.montant) FROM Transaction t WHERE t.type = :type")
    double sumByType(TypeTransaction type);
}

