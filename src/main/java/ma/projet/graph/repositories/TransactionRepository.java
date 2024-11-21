package ma.projet.graph.repositories;

import ma.projet.graph.entities.Transaction;
import ma.projet.graph.entities.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrnasactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t where t.type = :type")
    List<Transaction> findByType(TransactionType type);

    @Query("SELECT SUM(t.montant) FROM Transaction t")
    double sumMontants();

    void deleteById(Long id);
}
