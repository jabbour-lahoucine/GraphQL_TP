package ma.projet.graph.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Data
@Getter
@Setter
public class TransactionRequest {
    private Double montant;
    private String date;
    private TypeTransaction type;
    private Long compteId;

    // Getters et setters
}
