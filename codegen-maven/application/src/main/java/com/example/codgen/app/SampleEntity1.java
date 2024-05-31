package com.example.codgen.app;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
public class SampleEntity1 extends AbstractSampleEntity {
    @Id
    private Long id;

    private String data1;

    private BigDecimal money1;

    @ManyToOne(fetch = FetchType.LAZY)
    private SampleEntity1 parent;

    @OneToMany(fetch = FetchType.LAZY)
    private List<SampleEntity1> children;

}
