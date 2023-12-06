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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToOne
    @JoinColumn(name = "category", referencedColumnName = "id")
    private Category category;
}

