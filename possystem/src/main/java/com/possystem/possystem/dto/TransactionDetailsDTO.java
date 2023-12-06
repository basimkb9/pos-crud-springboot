package com.possystem.possystem.dto;
import com.possystem.possystem.domain.Transaction;
import com.possystem.possystem.domain.Variant;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TransactionDetailsDTO {

    private Long id;
    private Transaction transaction;
    private Variant variant;
    private Double unitPrice;
    private Double costPrice;
    private Long quantity;
    private Double subTotal;
    private Double profit;

}
