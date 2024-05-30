package com.example.codgen.app;


import javax.persistence.Entity;
import javax.persistence.Id;

import java.math.BigDecimal;

@Entity
public class SampleEntity1 {
    @Id
    private Long id;

    private String data1;

    private BigDecimal money1;

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
}
