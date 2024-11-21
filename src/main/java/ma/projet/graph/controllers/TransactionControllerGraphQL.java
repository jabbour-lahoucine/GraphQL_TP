package ma.projet.graph.controllers;

import lombok.AllArgsConstructor;
import ma.projet.graph.entities.Compte;
import ma.projet.graph.entities.Transaction;
import ma.projet.graph.entities.TransactionRequest;
import ma.projet.graph.entities.TypeTransaction;
import ma.projet.graph.repositories.CompteRepository;
import ma.projet.graph.repositories.TransactionRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class TransactionControllerGraphQL {

    private final TransactionRepository transactionRepository;
    private final CompteRepository compteRepository;

    @MutationMapping
    public Transaction addTransaction(@Argument("transaction") TransactionRequest transactionRequest) {
        if (transactionRequest == null) {
            throw new IllegalArgumentException("TransactionRequest cannot be null.");
        }

        // Conversion de la date
        Date parsedDate;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            parsedDate = dateFormat.parse(transactionRequest.getDate());
        } catch (ParseException e) {
            throw new RuntimeException("Invalid date format. Expected format: yyyy-MM-dd");
        }

        // Récupération du compte
        Compte compte = compteRepository.findById(transactionRequest.getCompteId())
                .orElseThrow(() -> new RuntimeException("Compte not found"));

        // Création et sauvegarde de la transaction
        Transaction transaction = new Transaction();
        transaction.setMontant(transactionRequest.getMontant());
        transaction.setDate(parsedDate);
        transaction.setType(transactionRequest.getType());
        transaction.setCompte(compte);

        transactionRepository.save(transaction);

        return transaction;
    }


    @QueryMapping
    public List<Transaction> compteTransactions(@Argument Long id) {
        Compte compte = compteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compte not found"));
        return transactionRepository.findByCompte(compte);
    }

    @QueryMapping
    public Map<String, Object> transactionStats() {
        long count = transactionRepository.count();
        double sumDepots = transactionRepository.sumByType(TypeTransaction.DEPOT);
        double sumRetraits = transactionRepository.sumByType(TypeTransaction.RETRAIT);

        return Map.of(
                "count", count,
                "sumDepots", sumDepots,
                "sumRetraits", sumRetraits
        );
    }
}
