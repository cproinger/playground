package com.example.codgen.app;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class GenericDTO {

    private Long id;

    private List<GenericValueDTO> values;

}
