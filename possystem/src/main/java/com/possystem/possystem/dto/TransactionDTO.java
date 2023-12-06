package com.possystem.possystem.dto;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TransactionDTO {
    private Long id;
    private LocalDate date;
    private Double totalAmount;
}
