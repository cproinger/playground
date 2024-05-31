package com.example.codgen.app;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class GenericValueDTO {
    private String name;
    private String stringValue;
    private BigDecimal bigDecimalValue;
}
