package com.possystem.possystem.domain;
import javax.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Data
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate date;
    private Double totalAmount;
}
