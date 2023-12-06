package com.possystem.possystem.dto;
import com.possystem.possystem.domain.Product;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VariantDTO {
    private Long id;
    @NotBlank(message = "Name cant be empty")
    private String name;
    @NotNull(message = "provide a cost price")
    @Min(value = 0)
    private Double costPrice;
    @NotNull(message = "provide the sale price")
    @Min(value = 0)
    private Double salePrice;
    @NotNull(message = "provide initial quantity")
    @Min(value = 0)
    private Long quantity;
    @NotBlank(message = "barcode cant be empty")
    private String barcode;
    @NotBlank(message = "product reference cant be empty")
    private Product product;
}
