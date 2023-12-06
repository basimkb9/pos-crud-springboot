package com.possystem.possystem.domain;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    private String productName;
    private String variantName;
    private Double salePrice;
    private Long quantity;
    private Double subTotal;
    private Double costPrice;
}
