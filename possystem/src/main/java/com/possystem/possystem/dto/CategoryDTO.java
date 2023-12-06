package com.possystem.possystem.dto;
import javax.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CategoryDTO {
    private Long id;
    @NotBlank(message = "name shouldn't be blank")
    private String name;
}
