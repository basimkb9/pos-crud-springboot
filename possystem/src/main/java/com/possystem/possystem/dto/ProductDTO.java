package com.possystem.possystem.dto;
import com.possystem.possystem.domain.Category;
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
public class ProductDTO {
    private Long id;
    @NotBlank(message = "Product name shouldn't be blank")
    private String name;
    @NotBlank(message = "product needs to be categorized, hence provide category info")
    private Category category;
}
