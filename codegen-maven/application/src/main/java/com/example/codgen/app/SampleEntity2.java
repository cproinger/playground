package com.example.codgen.app;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class SampleEntity2  extends AbstractSampleEntity {
    @Id
    private Long id;

    private String data2;

    private BigDecimal money2;

}
