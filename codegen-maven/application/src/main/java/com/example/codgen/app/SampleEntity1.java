package com.example.codgen.app;


import javax.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
public class SampleEntity1 {
    @Id
    private Long id;

    private String data1;

    private BigDecimal money1;

    @ManyToOne(fetch = FetchType.LAZY)
    private SampleEntity1 parent;

    @OneToMany(fetch = FetchType.LAZY)
    private List<SampleEntity1> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData1() {
        return data1;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public BigDecimal getMoney1() {
        return money1;
    }

    public void setMoney1(BigDecimal money1) {
        this.money1 = money1;
    }

    public SampleEntity1 getParent() {
        return parent;
    }

    public void setParent(SampleEntity1 parent) {
        this.parent = parent;
    }

    public List<SampleEntity1> getChildren() {
        return children;
    }

    public void setChildren(List<SampleEntity1> children) {
        this.children = children;
    }
}
