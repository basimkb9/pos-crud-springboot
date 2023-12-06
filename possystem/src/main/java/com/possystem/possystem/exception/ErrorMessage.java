package com.possystem.possystem.exception;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorMessage<T> {
    T body;
    LocalDateTime localTime;
}
