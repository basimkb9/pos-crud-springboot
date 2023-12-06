package com.possystem.possystem.domain;
import javax.persistence.*;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TransactionDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "transaction_id", referencedColumnName = "id")
    private Transaction transaction;
    @ManyToOne
    @JoinColumn(name = "variant_id", referencedColumnName = "id")
    private Variant variant;
    private Double unitPrice;
    private Double costPrice;
    private Long quantity;
    private Double subTotal;
    private Double profit;
}
